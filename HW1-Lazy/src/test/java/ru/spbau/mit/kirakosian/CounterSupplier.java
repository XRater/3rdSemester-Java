package ru.spbau.mit.kirakosian;

import java.util.function.Supplier;

/**
 * Simple supplier that returns target value on every get method call.
 *
 * In addition, counts number of get method calls.
 *
 * @param <T> type of the stored value.
 */
@SuppressWarnings("WeakerAccess")
class CounterSupplier<T> implements Supplier<T> {

    private int counter;
    private final T value;

    /**
     * Constructs supplier with the target value.
     *
     * @param value value to return on get method call.
     */
    public CounterSupplier(final T value) {
        this.value = value;
    }

    /**
     * Access to the counter.
     *
     * @return number of get method calls.
     */
    public int counter() {
        return counter;
    }

    @Override
    public T get() {
        counter++;
        return value;
    }
}
