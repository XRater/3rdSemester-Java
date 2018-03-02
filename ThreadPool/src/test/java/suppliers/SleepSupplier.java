package suppliers;

import com.google.common.base.Supplier;

public class SleepSupplier extends SupplierBase<Void> {

    @Override
    public Void get() {
        try {
//            System.out.println("Task " + " was started");
            Thread.sleep(100);
//            System.out.println("Task " + " was finished");
        } catch (final InterruptedException e) {
            // do nothing
        }
        return null;
    }
}
