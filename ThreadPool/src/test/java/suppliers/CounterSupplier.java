package suppliers;

import org.jetbrains.annotations.Nullable;

public class CounterSupplier extends BaseSupplier<Void> {

    private static int counter;
    private int myCounter;

    public int getCounter() {
        return counter;
    }

    @Nullable
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
