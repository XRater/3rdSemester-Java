package tasks;

import java.util.function.Supplier;

public interface LightFuture<T> {

    boolean isReady();

    T get();

}
