package com.spbau.mit.kirakosian;

import com.spbau.mit.kirakosian.suppliers.*;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import com.spbau.mit.kirakosian.threadPool.LightFuture;
import com.spbau.mit.kirakosian.threadPool.ThreadPool;
import com.spbau.mit.kirakosian.threadPool.ThreadPoolFactory;
import com.spbau.mit.kirakosian.threadPool.Utils;
import com.spbau.mit.kirakosian.threadPool.exceptions.LightExecutionException;
import com.spbau.mit.kirakosian.threadPool.exceptions.ThreadPoolIsTurnedDownException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("FieldCanBeLocal")
class ThreadPoolFactoryTest {

    private final int testsNumber = 100;
    private final int maxThreadsNumber = 10;

    /**
     * This test checks if every task was executed exactly one time.
     */
    @Test
    void testCallEveryTaskOnce() {
        for (int t = 0; t < testsNumber; t++) {
            final ThreadPool pool = ThreadPoolFactory.initThreadPool(3);
            final List<CounterSupplier> list = new LinkedList<>();

            for (int i = 0; i < 10; i++) {
                final CounterSupplier dummy = new CounterSupplier();
                list.add(dummy);
                pool.addTask(dummy);
            }

            pool.waitWithShutDown();

            assertEquals((t + 1) * 10, list.get(0).getCounter());
            for (final CounterSupplier counterSupplier : list) {
                assertEquals(1, counterSupplier.getMyCounter());
            }
        }
    }

    /**
     * This test checks if there at least n threads int the pool.
     * <p>
     * Initialises n - 1 thread with waiting tasks and notifying them with the last one.
     * If there are not enough threads in the pool, test will stuck, therefore there is
     * a timeout for test execution.
     */
    @Test
    void testALotOfThreads() {
        for (int i = 0; i < maxThreadsNumber; i++) {
            testNThreads(i * i / 2);
        }
    }

    private void testNThreads(final int threadsNumber) {

        final Thread timer = new Thread(() -> {
            Utils.sleep(5000);
            synchronized (this) {
                notify();
            }
        });
        timer.start();

        final ThreadPool pool = ThreadPoolFactory.initThreadPool(threadsNumber);
        final List<LightFuture<Void>> list = new LinkedList<>();

        for (int i = 0; i < threadsNumber - 1; i++) {
            list.add(pool.addTask(new WaitSupplier()));
        }
        pool.addTask(new SleepSupplier());
        pool.addTask(new NotifySupplier());

        new Thread(() -> {
            pool.waitWithShutDown();
            synchronized (this) {
                notify();
            }
        }).start();

        synchronized (this) {
            try {
                // there must be loop, but i cannot change variable
                // for it in lambda... What should I do with it?
                // Here I will hope that at least one test will fail.
                wait();
            } catch (@NotNull final InterruptedException e) {
                // do nothing
            }
            // Am i right that timer cannot still work?
            // In case thread was set on this one immediately?
            if (!timer.isAlive()) {
                fail("Out of time.");
            }
        }

        for (final LightFuture<Void> lightFuture : list) {
            assertTrue(lightFuture.isReady());
        }
    }

    @Test
    void testIsReady() {
        final ThreadPool pool = ThreadPoolFactory.initThreadPool(3);
        final LightFuture<Void> future = pool.addTask(new SleepSupplier(250));
        pool.addTask(new SleepSupplier());

        assertFalse(future.isReady());
        new Thread(() -> assertFalse(future.isReady())).start();
        Utils.sleep(500);
        assertTrue(future.isReady());
        new Thread(() -> assertTrue(future.isReady())).start();
    }

    @Test
    void testGet() {
        final ThreadPool pool = ThreadPoolFactory.initThreadPool(3);

        final ArrayList<LightFuture<Integer>> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(pool.addTask(new IntegerSupplier(i * i)));
        }

        final Runnable r = () -> {
            for (int i = 0; i < 10; i++) {
                assertEquals(Integer.valueOf(i * i), list.get(i).get());
            }
        };
        new Thread(r).start();
        new Thread(r).start();
        new Thread(r).start();
        r.run();
    }

    @Test
    void testShutDown() {
        final ThreadPool pool = ThreadPoolFactory.initThreadPool(3);
        final ArrayList<LightFuture<Void>> list = new ArrayList<>();

        list.add(pool.addTask(new SleepSupplier()));
        list.add(pool.addTask(new SleepSupplier()));
        list.add(pool.addTask(new SleepSupplier()));

        pool.shutdown();

        assertThrows(ThreadPoolIsTurnedDownException.class,
                () -> pool.addTask(new IntegerSupplier(1)));

        pool.waitWithShutDown();

        for (final LightFuture<Void> future : list) {
            assertTrue(future.isReady());
        }
    }

    @Test
    void testExceptions() {
        final ThreadPool pool = ThreadPoolFactory.initThreadPool(3);
        final LightFuture<Void> task = pool.addTask(() -> {
            throw new RuntimeException();
        });

        final Runnable check = () -> {
            assertThrows(LightExecutionException.class, task::get);
            assertThrows(LightExecutionException.class,
                    () -> task.thenApply(o -> 1).get());
        };

        check.run();
        new Thread(check).start();
        new Thread(check).start();
    }

    @Test
    void testThenApply() {
        final ThreadPool pool = ThreadPoolFactory.initThreadPool(3);

        final LightFuture<Integer> task = pool.addTask(() -> 1);
        for (int i = 0; i < 5; i++) {
            pool.addTask(new SleepSupplier());
        }

        final Runnable r = () -> assertEquals(Integer.valueOf(2), task.thenApply(n -> n + 1).get());
        r.run();
        new Thread(r).start();
        new Thread(r).start();
        new Thread(r).start();

        pool.waitWithShutDown();
    }
}