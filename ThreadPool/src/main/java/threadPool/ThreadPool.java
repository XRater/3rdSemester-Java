package threadPool;

import java.util.function.Supplier;

public interface ThreadPool {

    // in general, we may be able to work with any Task (Runnable interface),
    // but here we want to work with LightFuture tasks only to provide user
    // convenient interface to track tasks progress e.t.c.
    <T> LightFuture<T> addTask(Supplier<T> supplier);

    void shutdown();
}
