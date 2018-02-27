package ru.spbau.mit.kirakosian;

/**
 * Interface to store exactly one value and provide lazy evaluations. It means, that target
 * value will be evaluated only one time, on the first get method call.
 *
 * This class in some way similar to Singleton pattern.
 *
 * @param <T> type of the stored element.
 */
public interface Lazy<T> {

    /**
     * Returns stored in Lazy value. Evaluates this value if it is not known yet,
     * otherwise returns already evaluated value.
     */
    T get();

}
