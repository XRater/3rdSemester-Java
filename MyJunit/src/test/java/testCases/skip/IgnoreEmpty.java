package testCases.skip;

import annotations.Test;

public class IgnoreEmpty {

    @Test(ignore = "")
    void skipTest() {

    }

}
