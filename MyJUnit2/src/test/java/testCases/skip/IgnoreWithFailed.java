package testCases.skip;

import annotations.Test;

public class IgnoreWithFailed {

    @Test(ignore = "reason")
    void skipTest() {

    }

    @Test(expected = RuntimeException.class)
    void failedTest() {

    }

}
