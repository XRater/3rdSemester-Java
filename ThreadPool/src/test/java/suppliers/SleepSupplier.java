package suppliers;

import com.google.common.base.Supplier;

public class SleepSupplier extends SupplierBase<Void> {

    @Override
    public Void get() {
        try {
            Thread.sleep(100);
        } catch (final InterruptedException e) {
            // do nothing
        }
        return null;
    }
}
