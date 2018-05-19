package testCases.skip;

import annotations.Test;

public class IgnoreOne {

    @Test(ignore = "reason")
    void skipTest() {

    }

}
