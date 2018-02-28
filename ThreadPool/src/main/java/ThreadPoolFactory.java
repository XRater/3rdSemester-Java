import tasks.SleepTask;

import java.util.LinkedList;
import java.util.List;

public class ThreadPoolFactory {

    public static ThreadPool initThreadPool(final int threadsNumber) {
        return new ThreadPoolImpl(threadsNumber);
    }

    private static class ThreadPoolImpl implements ThreadPool {

        private final Runnable POISON_PILL = () -> {};
        private final int threadsNumber;

        private final BlockingQueue<Runnable> tasks = new BlockingQueue<>();
        private final List<Thread> threads = new LinkedList<>();
        private boolean isWorking = true;
        private final int shutdownTimeout = 1000;

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

            final Thread shutDownThread = new Thread(() -> {
                final double waitingBegin = System.currentTimeMillis();
                while (System.currentTimeMillis() - waitingBegin < shutdownTimeout) {
                    try {
                        Thread.sleep(shutdownTimeout);
                    } catch (final InterruptedException e) {
                        //do nothing
                    }
                }
                for (final Thread thread : threads) {
                    thread.interrupt();
                }
            });
            shutDownThread.start();
        }

        @Override
        public synchronized void addTask(final Runnable task) {
            if (!isWorking) {
                throw new ThreadPoolIsTurnedDownException();
            }
            if (task != POISON_PILL) {
                System.out.printf("Adding new task..." + '\n');
            }
            tasks.push(task );
        }

        private class ThreadPoolTask implements Runnable {

            @Override
            public void run() {
                System.out.println("Starting " + Thread.currentThread());
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

    public static void main(final String[] args) {
        final ThreadPool threadPool = initThreadPool(3);
        threadPool.addTask(new SleepTask());
//        threadPool.addTask(new SleepTask());
//        threadPool.addTask(new SleepTask());
//        threadPool.addTask(new SleepTask());
        threadPool.shutdown();
    }
}
