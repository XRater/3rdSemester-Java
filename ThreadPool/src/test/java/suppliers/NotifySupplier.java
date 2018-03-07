package suppliers;

import org.jetbrains.annotations.Nullable;

public class NotifySupplier extends BaseSupplier<Void> {

    @Nullable
    @Override
    public Void get() {
        synchronized (commonLock) {
            commonLock.notifyAll();
        }
        return null;
    }
}
