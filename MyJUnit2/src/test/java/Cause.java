public class Cause {

    private final Class<? extends Throwable> expected;
    private final Class<? extends Throwable> was;

    public Cause(final Class<? extends Throwable> expected, final Class<? extends Throwable> was) {
        this.expected = expected;
        this.was = was;
    }

    public Class<? extends Throwable> expected() {
        return expected;
    }

    public Class<? extends Throwable> was() {
        return was;
    }
}
