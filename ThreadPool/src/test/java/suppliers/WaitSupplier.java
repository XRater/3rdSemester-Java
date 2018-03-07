package suppliers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WaitSupplier extends BaseSupplier<Void> {

    @Nullable
    @Override
    public Void get() {
        synchronized (commonLock) {
            try {
                commonLock.wait();
            } catch (@NotNull final InterruptedException e) {
                //do nothing
            }
        }
        return null;
    }

}
