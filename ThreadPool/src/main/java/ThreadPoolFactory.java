import tasks.LightFuture;
import tasks.Task;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings("WeakerAccess")
public class ThreadPoolFactory {

    public static ThreadPool initThreadPool(final int threadsNumber) {
        return new ThreadPoolImpl(threadsNumber);
    }

    private static class ThreadPoolImpl implements ThreadPool {

        private final Supplier<Void> POISON_PILL = () -> null;
        private final int threadsNumber;

        private final BlockingQueue<Runnable> tasks = new BlockingQueue<>();
        private final List<Thread> threads = new LinkedList<>();
        private boolean isWorking = true;

        private ThreadPoolImpl(final int threadsNumber) {
            this.threadsNumber = threadsNumber;
            for (int i = 0; i < threadsNumber; i++) {
                threads.add(new Thread(new ThreadPoolTask()));
            }
            for (final Thread thread : threads) {
                thread.start();
            }
        }

        @Override
        public synchronized void shutdown() {
            for (int i = 0; i < threadsNumber; i++) {
                addTask(POISON_PILL);
            }
            isWorking = false;
        }

        @Override
        public synchronized <T> LightFuture<T> addTask(final Supplier<T> supplier) {
            if (!isWorking) {
                throw new ThreadPoolIsTurnedDownException();
            }
            final LightFuture<T> lightFuture = new Task<>(supplier);
            tasks.push(lightFuture);
            return lightFuture;
        }

        private class ThreadPoolTask implements Runnable {

            @Override
            public void run() {
                while (true) {
                    final Runnable task = tasks.pop();
                    if (task == POISON_PILL) {
                        break;
                    }
                    task.run();
                }
                System.out.println("Stopping " + Thread.currentThread());
            }
        }
    }
}
