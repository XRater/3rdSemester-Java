import java.util.Map;
import java.util.Set;

@SuppressWarnings("WeakerAccess")
public class TestResults {

    private final String className;
    private final Map<String, Cause> testsFailed;
    private final Map<String, String> testsSkipped;
    private final Set<String> testsPassed;
    private final int testsStarted;

    TestResults(final String className,
                final Map<String, Cause> failedTests,
                final Map<String, String> skippedTests,
                final Set<String> passedTests) {

        this.className = className;
        testsSkipped = skippedTests;
        testsFailed = failedTests;
        testsPassed = passedTests;
        testsStarted = testsFailed.size() + testsPassed.size();
    }

    public String className() {
        return className;
    }

    public Map<String, Cause> testsFailed() {
        return testsFailed;
    }

    public Map<String, String> testsSkipped() {
        return testsSkipped;
    }

    public Set<String> testsPassed() {
        return testsPassed;
    }

    public int testsStarted() {
        return testsStarted;
    }

}
