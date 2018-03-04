package suppliers;

import java.util.function.Supplier;

abstract class SupplierBase<T> implements Supplier<T> {

    static final Object commonLock = new Object();

}
