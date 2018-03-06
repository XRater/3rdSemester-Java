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
}
