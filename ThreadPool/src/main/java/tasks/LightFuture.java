package tasks;

import java.util.function.Supplier;

public interface LightFuture<T> extends Runnable {

    boolean isReady();
}
