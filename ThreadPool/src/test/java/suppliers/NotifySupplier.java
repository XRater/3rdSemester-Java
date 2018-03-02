package suppliers;

public class NotifySupplier extends SupplierBase<Void> {

    @Override
    public Void get() {
        synchronized (commonLock) {
            System.out.println("Notifying all!");
            commonLock.notifyAll();
        }
        return null;
    }
}
