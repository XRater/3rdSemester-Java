package threadPool;

import java.util.LinkedList;
import java.util.Queue;

/**
 * This class provides safe concurrent queue.
 *
 * In other words, this class is a wrapper around simple queue class,
 * but in addition every action in concurrent program will be safe, that means,
 * that you are not able to override any values in your queue, or to take
 * the same value twice.
 *
 * @param <T> type of the queue.
 */
@SuppressWarnings("WeakerAccess")
public class BlockingQueue<T> {

    private final Queue<T> tasks = new LinkedList<>();

    /**
     * Adds new item to the end of the queue.
     *
     * @param item item to add into queue.
     */
    public void push(final T item) {
        synchronized (tasks) {
            tasks.add(item);
            tasks.notify();
        }
    }

    /**
     * The method extracts top item from the queue.
     *
     * @return top item of the queue.
     */
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