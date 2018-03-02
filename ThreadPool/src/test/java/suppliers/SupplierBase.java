package suppliers;

import java.util.function.Supplier;

abstract class SupplierBase<T> implements Supplier<T> {

    protected static int counter;
    protected static final Object commonLock = new Object();

    SupplierBase() {
        counter++;
    }

}
