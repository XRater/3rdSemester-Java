package threadPool;

import java.util.function.Function;
import java.util.function.Supplier;

public interface LightFuture<T> extends Supplier<T> {

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
