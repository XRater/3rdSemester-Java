package com.spbau.mit.kirakosian.suppliers;

import java.util.function.Supplier;

/**
 * Base class for some simple com.spbau.mit.kirakosian.suppliers, which may be used in tests.
 *
 * @param <T> type of stored value.
 */
abstract class BaseSupplier<T> implements Supplier<T> {

    static final Object commonLock = new Object();

}
