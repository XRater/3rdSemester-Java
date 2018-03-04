package suppliers;

public class NotifySupplier extends SupplierBase<Void> {

    @Override
    public Void get() {
        synchronized (commonLock) {
            commonLock.notifyAll();
        }
        return null;
    }
}
