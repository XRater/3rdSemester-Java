import java.util.concurrent.BlockingQueue;

public interface ThreadPool {

    void addTask(Runnable task);

    void shutdown();
}
