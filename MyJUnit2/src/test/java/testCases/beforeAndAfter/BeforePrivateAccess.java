package testCases.beforeAndAfter;

import annotations.Before;
import annotations.Test;

public class BeforePrivateAccess {

    private static int x = 0;

    @Before
    private void before() {
        x = 1;
    }

    @Test
    public void test() {
        if (x != 1) {
            throw new RuntimeException();
        }
        x = 0;
    }

    public static int x() {
        return x;
    }
}
