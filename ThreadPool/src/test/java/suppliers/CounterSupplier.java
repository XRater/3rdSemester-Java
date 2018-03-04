package suppliers;

public class CounterSupplier extends SupplierBase<Void> {

    private static int counter;
    private int myCounter;

    public int getCounter() {
        return counter;
    }

    @Override
    public Void get() {
        synchronized (this) {
            myCounter++;
        }
        synchronized (commonLock) {
            counter++;
        }
        return null;
    }

    public int getMyCounter() {
        return myCounter;
    }

}
