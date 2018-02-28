import java.util.LinkedList;
import java.util.Queue;

public class ThreadPoolFactory {

    public static ThreadPool initThreadPool(final int threadsNumber) {
        return new ThreadPoolImpl(threadsNumber);
    }

    private static class ThreadPoolImpl implements ThreadPool {

        private final int threadsNumber;

        private final Queue<Runnable> tasks = new LinkedList<>();

        private ThreadPoolImpl(final int threadsNumber) {
            this.threadsNumber = threadsNumber;
        }

        @Override
        public void addTask(final Runnable task) {

        }

    }
}
