package threadPool.exceptions;

public class LightExecutionException extends RuntimeException {

    @SuppressWarnings("unused")
    public LightExecutionException() {
        super();
    }

    public LightExecutionException(final Exception error) {
        super(error);
    }
}
