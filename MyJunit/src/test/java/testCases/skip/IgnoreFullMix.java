package testCases.skip;

import annotations.Test;

public class IgnoreFullMix {

    @Test(ignore = "reason")
    void skipTest() {

    }

    @Test(expected = RuntimeException.class)
    void failedTest() {

    }

    @Test
    void passedTest() {

    }

}
