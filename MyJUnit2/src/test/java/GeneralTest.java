import annotations.Test;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("WeakerAccess")
public class GeneralTest {

    static void testClass(final Class<?> clazz, final TestResults results)
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();

        final PrintStream old = System.out;
        System.setOut(new PrintStream(os));

        MyJUnitLauncher.testClass(clazz);

        final String result = os.toString();
        final String[] lines = result.split("\n");

        System.setOut(old);
        for (final String line : lines) {
            System.out.println(line);
        }

        removeColours(lines);

        checkPassed(lines, results);
        checkFailed(lines, results);
        checkSkipped(lines, results);
        checkResults(lines, results);
        checkGeneral(lines, results);


        assertThat(lines.length, is(results.testsPassed().size() * 3  +
                results.testsSkipped().size() * 3 + results.testsFailed().size() * 4 + 6));
    }

    private static void checkSkipped(final String[] lines, final TestResults results) {
        final Set<String> skipped = new TreeSet<>();
        for (int i = 1; i < lines.length - 5; i++) {
            if (lines[i].endsWith("SKIPPED")) {
                final String testName = lines[i].split("\"")[1];
                final String printedReason = lines[i + 1].substring(9, lines[i + 1].length() - 1);
                skipped.add(testName);

                final String expectedReason = results.testsSkipped().get(testName);
                assertThat(printedReason, is(expectedReason));

                i++;
            }
        }
        assertThat(skipped, is(results.testsSkipped().keySet()));
    }

    private static void checkFailed(final String[] lines, final TestResults results) {
        final Set<String> failed = new TreeSet<>();
        for (int i = 1; i < lines.length - 5; i++) {
            if (lines[i].endsWith("FAILED")) {
                final String testName = lines[i].split("\"")[1];
                final String expected = lines[i + 1].split(" ")[4];
                final String was = lines[i + 1].split(" ")[7];
                failed.add(testName);

                final Class<?> gottenRes = results.testsFailed().get(testName).was();
                assertThat(expected, is(results.testsFailed().get(testName).expected().getName()));
                assertThat(was, is(gottenRes == null ? Test.NONE.class.getName() : gottenRes.getName()));

                i++;
            }
        }
        assertThat(failed, is(results.testsFailed().keySet()));
    }

    private static void checkPassed(final String[] lines, final TestResults results) {
        final Set<String> accepted = new TreeSet<>();
        for (int i = 1; i < lines.length - 5; i++) {
            if (lines[i].endsWith("OK")) {
                accepted.add(lines[i].split("\"")[1]);
            }
        }
        assertThat(accepted, is(results.testsPassed()));
    }

    private static void checkResults(final String[] lines, final TestResults results) {
        assertThat(lines[lines.length - 3], is("Failed tests number: " + results.testsFailed().size()));
        assertThat(lines[lines.length - 2], is("Skipped tests number: " + results.testsSkipped().size()));
        assertThat(lines[lines.length - 1], is("Passed tests number: " + results.testsPassed().size()));
    }

    private static void checkGeneral(final String[] lines, final TestResults results) {
        assertThat(lines[0], is("Running tests for class \"" + results.className() + "\":"));
        assertThat(lines[lines.length - 4], startsWith("Testing finished in "));
    }

    private static void removeColours(final String[] lines) {
        for (int i = 0; i < lines.length; i++) {
            for (final String colour : Colours.getColours()) {
                lines[i] = lines[i].replace(colour, "");
            }
        }
    }
}
