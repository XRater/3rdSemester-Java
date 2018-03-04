package threadPool;

import java.util.function.Supplier;

public interface LightFuture<T> extends Supplier<T> {

    boolean isReady();

}
