package threadPool;

/**
 *  Class with factory methods to create ThreadPool interface classes.
 *
 *  By this class we hide out {@link ThreadPoolImpl} class and show only
 *  {@link ThreadPool} interface.
 */
@SuppressWarnings("WeakerAccess")
public class ThreadPoolFactory {

    public static ThreadPool initThreadPool(final int threadsNumber) {
        return new ThreadPoolImpl(threadsNumber);
    }
}
