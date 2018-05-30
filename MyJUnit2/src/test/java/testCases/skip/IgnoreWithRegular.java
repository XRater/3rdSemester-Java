package testCases.skip;

import annotations.Test;

public class IgnoreWithRegular {

    @Test(ignore = "reason")
    void skipTest() {

    }

    @Test
    void passedTest() {

    }

}
