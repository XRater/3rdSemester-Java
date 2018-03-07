package threadPool;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This interface provides methods for convenient tracking of your tasks.
 * <p>
 * For example, you may check if execution of the task already has ended with
 * {@link LightFuture#isReady()}, get result of the calculation at any time (task
 * may be not finished yet) or add new tasks with dependency on the result of the
 * calculation.
 *
 * @param <T>
 */
public interface LightFuture<T> extends Supplier<T> {

    /**
     * The method cheks if any the task was already executed. Task may end
     * with an error, this method still will return true.
     *
     * @return true if task was finished already and false otherwise.
     */
    boolean isReady();

    /**
     * The method adds new task to the threadPool, which depends on the result of
     * the calculation. That means, that task cannot be evaluated until its parent task
     * is not ready.
     * <p>
     * The method takes function f, and applies it to the result of the calculation.
     *
     * @param f   function to apply
     * @param <U> type of the function result
     * @return new LightFuture task, which was already added to the pool.
     */
    <U> LightFuture<U> thenApply(Function<T, ? extends U> f);

}
