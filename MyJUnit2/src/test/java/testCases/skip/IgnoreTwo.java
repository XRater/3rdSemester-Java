package testCases.skip;

import annotations.Test;

public class IgnoreTwo {

    @Test(ignore = "reason1")
    void skipTest1() {

    }

    @Test(ignore = "reason2")
    void skipTest2() {

    }

}
