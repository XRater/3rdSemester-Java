package threadPool;

import threadPool.exceptions.LightExecutionException;

import java.util.function.Supplier;

/**
 * Simple LightFuture interface implementation,
 *
 * @param <T> return type of execution
 */
// This class should not be seen by anyone from outside, in addition, run method
// is not expected to be executed in multi-thread environment.
class Task<T> implements LightFuture<T>, Runnable {

    private final Supplier<T> supplier;

    private boolean ready;
    private T value;

    private Exception error;

    Task(final Supplier<T> supplier) {
        this.supplier = supplier;
    }

    @Override
    public boolean isReady() {
        return ready;
    }

    @Override
    public T get() {
        while (!ready) { // waiting for result
            try {
                wait();
            } catch (final InterruptedException e) {
                // nothing to do here
            }
        }
        if (error != null) {
            throw new LightExecutionException(error);
        }
        return value;
    }

    @Override
    public synchronized void run() {
        if (!ready) { // we may execute every task only one time
            try {
                value = supplier.get();
            } catch (final Exception e) {
                error = e;
            }
            ready = true;
            notifyAll();
        } else {
            throw new RuntimeException(); // should never happen, cause it is an inner method
        }
    }
}
