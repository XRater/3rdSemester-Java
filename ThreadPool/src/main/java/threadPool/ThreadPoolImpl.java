package threadPool;

import threadPool.exceptions.LightExecutionException;
import threadPool.exceptions.TaskIsReadyAlreadException;
import threadPool.exceptions.ThreadPoolIsTurnedDownException;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * {@link ThreadPool}
 * <p>
 * Basic ThreadPool interface implementation.
 * <p>
 * This class supports safe work in concurrent environment. Class is not visible outside
 * from the package, therefore it is possible to work with only with public methods
 * of {@link ThreadPool} interface.
 * <p>
 * In general, this pool supports only tasks, which implements LightFuture interface,
 * but there is an easy way to work with any Runnable tasks, therefore blocking queue
 * inside of the class stores any kind of Runnable classes.
 */
class ThreadPoolImpl implements ThreadPool {

    private final Supplier<Void> POISON_PILL = () -> null;
    private final Task<Void> POISON_PILL_TASK = new Task<>(POISON_PILL);

    // Locks
    private final Object shutDownLock = new Object();

    private final BlockingQueue<Runnable> tasks = new BlockingQueue<>();

    private final int threadsNumber;
    private boolean isWorking = true;
    private int threadsStillWorking;


    ThreadPoolImpl(final int threadsNumber) {
        this.threadsNumber = threadsNumber;
        threadsStillWorking = threadsNumber;
        final List<Thread> threads = new LinkedList<>();
        for (int i = 0; i < threadsNumber; i++) {
            threads.add(new Thread(new ThreadTask()));
        }
        for (final Thread thread : threads) {
            thread.start();
        }
    }

    /**
     * Poison pill pattern is used for {@link ThreadPool#shutdown()} implementation.
     */
    @Override
    public synchronized void shutdown() {
        if (isWorking) { // otherwise, we may do this section twice
            for (int i = 0; i < threadsNumber; i++) {
                addTask(POISON_PILL);
            }
        }
        isWorking = false;
    }

    /**
     * {@link ThreadPool#waitWithShutDown()}
     */
    @Override
    public void waitWithShutDown() {
        shutdown();
        synchronized (shutDownLock) {
            while (threadsStillWorking != 0) {
                try {
                    shutDownLock.wait();
                } catch (final InterruptedException e) {
                    // do nothing
                }
            }
        }
    }

    /**
     * {@link ThreadPool#addTask(Supplier)}
     */
    @Override
    public synchronized <T> LightFuture<T> addTask(final Supplier<T> supplier) {
        if (!isWorking) {
            throw new ThreadPoolIsTurnedDownException();
        }
        if (supplier == POISON_PILL) {
            tasks.push(POISON_PILL_TASK);
            return null;
        }
        final Task<T> task = new Task<>(supplier);
        tasks.push(task);
        return task;
    }

    /**
     * Default task for every thread in the thread pool.
     * <p>
     * Taking tasks from the queue until thread pool is working.
     */
    private class ThreadTask implements Runnable {

        @Override
        public void run() {
            while (true) {
                final Runnable task = tasks.pop();
                if (task == POISON_PILL_TASK) {
                    synchronized (shutDownLock) {
                        threadsStillWorking--;
                        if (threadsStillWorking == 0) {
                            shutDownLock.notifyAll();
                        }
                    }
                    break;
                }
                task.run();
            }
        }
    }

    /**
     * Simple LightFuture interface implementation, that may be used inside
     * ThreadPoolImpl class. This class is not visible from outside, therefore you are
     * not able to work with this class directly.
     * <p>
     * Public interface of this class is safe in concurrent environment.
     * <p>
     * Talking about not visible for user interface (such as method run, that is not
     * visible with the interface LightFuture) it is expected, that programmer
     * works safe with this methods (for example, calls run method twice). If there was
     * any mistake in the code, TaskIsReadyAlreadyException is expected to be thrown.
     * <p>
     * If an exception occurred during the task execution this exception will be stored
     * inside of the class and passed to every future calculations (thenApply method).
     * In case of trying to get result of the failed calculation or execute any
     * corresponded on the answer task LightExecutionException will be thrown.
     *
     * @param <T> return type of execution
     */
    private class Task<T> implements LightFuture<T>, Runnable {

        private final Supplier<T> supplier;

        private boolean ready;
        private T value;
        private Exception error;

        private Task(final Supplier<T> supplier) {
            this.supplier = supplier;
        }

        /**
         * Task is ready if it execution was ended by any thread. It does not matter
         * either it was successful or not (ended with an exception).
         * <p>
         * {@link LightFuture#isReady()}
         *
         * @return true if task was already completed and false otherwise.
         */
        @Override
        public boolean isReady() {
            return ready;
        }

        /**
         * Returns the value of the calculation. If calculation is not ready yet, waits
         * for its end and then returns the result.
         * <p>
         * If there was an error during execution LightExecutionException with
         * cause of the fail inside will be thrown.
         * <p>
         * {@link LightFuture#get()}
         *
         * @return result of the calculation.
         */
        @Override
        public T get() {
            synchronized (this) {
                while (!ready) { // waiting for result
                    try {
                        wait();
                    } catch (final InterruptedException e) {
                        // nothing to do here
                    }
                }
            }
            if (error != null) {
                throw new LightExecutionException(error);
            }
            return value;
        }


        /**
         * {@link LightFuture#thenApply(Function)}
         */
        @Override
        public <U> LightFuture<U> thenApply(final Function<T, ? extends U> f) {
            final Supplier<U> supplier = () -> f.apply(Task.this.get());
            return addTask(supplier);
        }

        /**
         * Inner method of class. Should not be visible from outside (of course it is
         * possible to call this method with cast of this object to Runnable,
         * but let us think that it is a user problem).
         * <p>
         * This method is called, when thread from the pool staring to execute this task.
         * Runnable interface is supported for this very reason.
         */
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
                throw new TaskIsReadyAlreadException(); // should never happen, cause it is an inner method
            }
        }
    }
}
