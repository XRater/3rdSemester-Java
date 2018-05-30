class Cause {

    private final Class<? extends Throwable> expected;
    private final Class<? extends Throwable> was;

    Cause(final Class<? extends Throwable> expected, final Class<? extends Throwable> was) {
        this.expected = expected;
        this.was = was;
    }

    Class<? extends Throwable> expected() {
        return expected;
    }

    Class<? extends Throwable> was() {
        return was;
    }
}
