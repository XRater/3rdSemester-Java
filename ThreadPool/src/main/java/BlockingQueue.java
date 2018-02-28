import java.util.LinkedList;
import java.util.Queue;

public class BlockingQueue<T> {

    private final Queue<T> tasks = new LinkedList<>();

    public void push(final T task) {
        synchronized (tasks) {
            tasks.add(task);
            tasks.notify();
        }
    }

    public T pop() {
        synchronized (tasks) {
            while (tasks.isEmpty()) {
                try {
                    tasks.wait();
                } catch (final InterruptedException e) {
                    // do nothing
                }
            }
            return tasks.poll();
        }
    }
}