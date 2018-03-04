package threadPool;

import threadPool.exceptions.ThreadPoolIsTurnedDownException;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

/**
 *
 */
@SuppressWarnings("WeakerAccess")
public class ThreadPoolFactory {

    public static ThreadPool initThreadPool(final int threadsNumber) {
        return new ThreadPoolImpl(threadsNumber);
    }

    private static class ThreadPoolImpl implements ThreadPool {

        private final Supplier<Void> POISON_PILL = () -> null;
        private final Task<Void> POISON_PILL_TASK = new Task<>(POISON_PILL);


        // Locks
        private final Object shutDownLock = new Object();

        private final BlockingQueue<Runnable> tasks = new BlockingQueue<>();
        private final List<Thread> threads = new LinkedList<>();

        private final int threadsNumber;
        private boolean isWorking = true;
        private int threadsStillWorking;


        private ThreadPoolImpl(final int threadsNumber) {
            this.threadsNumber = threadsNumber;
            threadsStillWorking = threadsNumber;
            for (int i = 0; i < threadsNumber; i++) {
                threads.add(new Thread(new ThreadTask()));
            }
            for (final Thread thread : threads) {
                thread.start();
            }
        }

        /**
         * {@link ThreadPool#shutdown()}
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
//                         do nothing
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
         *
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
    }
}
