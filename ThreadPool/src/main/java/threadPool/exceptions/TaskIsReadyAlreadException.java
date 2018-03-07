package threadPool.exceptions;

/**
 * This exception may be thrown if implementation of {@link threadPool.LightFuture}
 * interface methods was not concurrent safe.
 * <p>
 * For example, if tasks method "run" was called more then one time.
 */
public class TaskIsReadyAlreadException extends RuntimeException {

}
