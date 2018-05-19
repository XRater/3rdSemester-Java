package ru.spbau.mit.kirakosian;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SuppressWarnings("FieldCanBeLocal")
class LazyFactoryTest {

    private final int threadsNumber = 10;
    private final int testsNumber = 100;

    @Test
    void testCreateUnsynchronizedLazye() {
        final CounterSupplier<String> stringSupplier = new CounterSupplier<>("hello");
        final CounterSupplier<String> nullSupplier = new CounterSupplier<>(null);

        assertEquals(stringSupplier.counter(), 0);
        assertEquals(nullSupplier.counter(), 0);

        final Lazy<String> stringLazy = LazyFactory.createUnsynchronizedLazy(stringSupplier);
        final Lazy<String> nullLazy = LazyFactory.createUnsynchronizedLazy(nullSupplier);

        assertEquals("hello", stringLazy.get());
        assertEquals("hello", stringLazy.get());
        assertEquals("hello", stringLazy.get());

        assertNull(nullLazy.get());
        assertNull(nullLazy.get());
        assertNull(nullLazy.get());
        assertNull(nullLazy.get());

        assertEquals(1, stringSupplier.counter());
        assertEquals(1, nullSupplier.counter());
    }

    @Test
    void testCreateSynchronizedLazy() {
        for (int i = 0; i < testsNumber; i++) {
            try {
                oneCreateSynchronizedLazyTest("hello");
                oneCreateSynchronizedLazyTest(null);
            } catch (@NotNull final InterruptedException e) {
                // no idea what to do here... It is not a failure
            }
        }
    }

    private void oneCreateSynchronizedLazyTest(final String testValue) throws InterruptedException {
        final CounterSupplier<String> supplier = new  CounterSupplier<>(testValue);
        final Lazy<String> lazy = LazyFactory.createSynchronizedLazy(supplier);

        final List<Thread> tasks = new LinkedList<>();
        for (int i = 0; i < threadsNumber; i++) {
            tasks.add(new Thread(() -> assertEquals(testValue, lazy.get())));
        }

        for (final Thread task : tasks) {
            task.start();
        }

        for (final Thread task : tasks) {
            task.join();
        }
        assertEquals(supplier.counter(), 1);
    }

}