package tasks;

import java.util.function.Supplier;

@SuppressWarnings("WeakerAccess")
public class Task<T> implements LightFuture<T> {

    private final Supplier<T> supplier;

    private boolean ready;
    private T value;

    public Task(final Supplier<T> supplier) {
        this.supplier = supplier;
    }

    @Override
    public boolean isReady() {
        return ready;
    }

    @Override
    public void run() {
        value = supplier.get();
        ready = true;
    }
}
