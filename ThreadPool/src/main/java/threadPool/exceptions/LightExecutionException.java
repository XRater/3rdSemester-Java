package threadPool.exceptions;

/**
 * This class provides exception to be thrown in case of calling get method
 * of calculation, which was failed with th exception.
 */
public class LightExecutionException extends RuntimeException {

    @SuppressWarnings("unused")
    public LightExecutionException() {
        super();
    }

    public LightExecutionException(final Exception error) {
        super(error);
    }
}
