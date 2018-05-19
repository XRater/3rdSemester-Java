package com.spbau.mit.kirakosian.threadPool;

import java.util.function.Supplier;

// In general, we may be able to work with any Task (Runnable interface),
// but here we want to work with LightFuture tasks only to provide user
// convenient interface to track tasks progress e.t.c.

/**
 * This interface provides base thread pool interface, which means that you are
 * able to add new tasks to the pool and to call shutdown method, which turns the pool off.
 * <p>
 * In addition, the pool provides convenient interface to track your tasks progress
 * with the LightFuture interface. Every task will be wrapped in this interface.
 */
public interface ThreadPool {

    /**
     * The method adds new task to the thread pool. Added tasks is equals to one "get"
     * calculation of the supplier.
     * <p>
     * In case of trying to add new task after shutdown ThreadPoolIsTurnedDownException
     * will be thrown.
     *
     * @param supplier supplier to create task from.
     * @param <T>      type of the result of calculation.
     * @return new LightFuture object to track your task state.
     */
    <T> LightFuture<T> addTask(Supplier<T> supplier);

    /**
     * Tells pool to shutdown. After calling this method no more tasks
     * will be applied to the pool. All tasks, that are already stored
     * will be finished.
     * <p>
     * In case of trying to add new task after shutdown ThreadPoolIsTurnedDownException
     * will be thrown.
     */
    void shutdown();

    /**
     * This method stops your main thread until all tasks in poll will be executed
     * and calls shutdown method of the pool, therefore after calling this method
     * you might be sure that all tasks added to the pool are already finished.
     */
    void waitWithShutDown();
}
