package ru.spbau.mit.kirakosian;

import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

/**
 * Factory class to produce different ru.spbau.mit.kirakosian.Lazy classes. Unsynchronized and synchronized versions
 * are supported.
 */
@SuppressWarnings("WeakerAccess")
public class LazyFactory {

    /**
     * Basic method to create unsynchronized lazy class from the given supplier.
     *
     * @param supplier supplier to get element from.
     * @param <T> type of the stored in ru.spbau.mit.kirakosian.Lazy element
     * @return new concurrent unsafe ru.spbau.mit.kirakosian.Lazy instance
     */
    public static <T> Lazy<T> createUnsynchronizedLazy(final Supplier<T> supplier) {
        // there was a variant to use anonymous class instead of inner static,
        // but with this implementation our code is a bit more flexible. For example, if
        // we want to support something else instead of supplier we may use the same class,
        // but with the different constructor.
        return new LazyUnsync<>(supplier);
    }

    /**
     * Basic method to create synchronized lazy class from the given supplier.
     *
     * @param supplier supplier to get element from.
     * @param <T> type of the stored in Lazy element
     * @return new concurrent safe ru.spbau.mit.kirakosian.Lazy instance
     */
    public static <T> Lazy<T> createSynchronizedLazy(final Supplier<T> supplier) {
        return new LazySync<>(supplier);
    }

    /**
     * Basic ru.spbau.mit.kirakosian.Lazy implementation. This class is not recommended to use in concurrent programs,
     * use LazySync implementation instead.
     *
     * @param <T> type of the stored value
     */
    private static class LazyUnsync<T> implements Lazy<T> {

        @Nullable
        private Supplier<T> supplier;
        private T value;

        LazyUnsync(@Nullable final Supplier<T> supplier) {
            this.supplier = supplier;
        }

        // as i see, if there is no description the one from the interface will be shown
        // it looks more beautiful then the link in my opinion
        @Override
        public T get() {
            if (supplier != null) {
                value = supplier.get();
                supplier = null;
            }
            return value;
        }
    }

    /**
     * Synchronized version of LazyUnsync class. Supports correct work with many threads.
     *
     * @param <T> type the of stored value.
     */
    private static class LazySync<T> implements Lazy<T> {

        @Nullable
        private volatile Supplier<T> supplier;
        private T value;

        LazySync(@Nullable final Supplier<T> supplier) {
            this.supplier = supplier;
        }

        @Override
        public T get() {
            if (supplier != null) {
                synchronized (this) {
                    if (supplier != null) { // check that we have not done this section yet
                        //noinspection ConstantConditions
                        value = supplier.get();
                        supplier = null;
                    }
                }
            }
            return value;
        }
    }

}
