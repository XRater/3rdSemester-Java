import tasks.LightFuture;

import java.util.concurrent.BlockingQueue;
import java.util.function.Supplier;

public interface ThreadPool {

    <T> LightFuture<T> addTask(Supplier<T> supplier);

    void shutdown();
}
