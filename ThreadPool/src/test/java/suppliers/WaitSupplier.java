package suppliers;

public class WaitSupplier extends SupplierBase<Void> {

    @Override
    public Void get() {
        synchronized (commonLock) {
            try {
                commonLock.wait();
            } catch (final InterruptedException e) {
                //do nothing
            }
        }
        return null;
    }

}
