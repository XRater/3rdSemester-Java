import annotations.Test;
import com.google.common.base.Stopwatch;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;

public class Logger {

    private final Class<?> clazz;

    private final Stopwatch globalClock = Stopwatch.createUnstarted();
    private final Stopwatch localClock = Stopwatch.createUnstarted();
    private Method calledMethod;

    private int testsPassed;
    private int testsSkipped;
    private int testsFailed;

    Logger(final Class<?> clazz, final Object object) {
        if (clazz != object.getClass()) {
            throw new IllegalArgumentException();
        }
        this.clazz = clazz;
    }

    void start() {
        globalClock.start();
        System.out.println("Running tests for class \"" +
                colour(clazz.getName(), Colours.ANSI_BLUE) + "\":");
        printDelimiterLine();
    }

    void finish() {
        globalClock.stop();
        System.out.println("Testing finished in " + globalClock);
        System.out.println("Failed tests number: " + testsFailed);
        System.out.println("Skipped tests number: " + testsSkipped);
        System.out.print("Passed tests number: " + testsPassed);
    }

    void logMethodStart(final Method method) {
        calledMethod = method;
        localClock.reset();
        localClock.start();

        System.out.println("Starting test \""
                + colour(method.getName(), Colours.ANSI_BLUE) + "\"");
    }

    void logTestPassed(final Method method) {
        testsPassed++;
        assert(method == calledMethod);
        localClock.stop();
        System.out.println("Test \"" + colour(method.getName(), Colours.ANSI_BLUE)
                + "\" passed in " + localClock + " | Verdict: "
                + colour("OK", Colours.ANSI_GREEN));
        printDelimiterLine();
        calledMethod = null;
    }

    public void logTestSkipped(@NotNull final Method method, final String reason) {
        testsSkipped++;
        System.out.println("Test \"" + colour(method.getName(), Colours.ANSI_BLUE)
                + "\" skipped in " + localClock + " | Verdict: "
                + colour("SKIPPED", Colours.ANSI_YELLOW));
        System.out.println("Reason: \"" + reason + "\"");
        printDelimiterLine();
        calledMethod = null;
    }

    void logTestFailed(final Method method, final Class<?> expected, final Class<?> gotten) {
        testsFailed++;
        assert(method == calledMethod);
        localClock.stop();
        System.out.println("Test \"" + colour(method.getName(), Colours.ANSI_BLUE)
                + "\" failed in " + localClock + " | Verdict: "
                + colour("FAILED", Colours.ANSI_RED));
        final String gottenString = gotten == null ? Test.NONE.class.getName() : gotten.getName();
        System.out.println(colour("Excpected exception type is " + expected.getName()
                + " but was " + gottenString, Colours.ANSI_RED));
        printDelimiterLine();
        calledMethod = null;
    }

    private void printDelimiterLine() {
        System.out.println("---------------------------------------------------------------------------");
    }

    private String colour(final String s, final String colour) {
        return colour + s + Colours.ANSI_RESET;
    }
}
