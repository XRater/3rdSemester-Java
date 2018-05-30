package testCases.beforeAndAfter;

import annotations.After;
import annotations.Test;

public class AfterPrivateAccess {

    private static int x = 1;

    @After
    private void before() {
        x = 1;
    }

    @Test
    public void test1() {
        if (x != 1) {
            throw new RuntimeException();
        }
        x = 0;
    }

    @Test
    public void test2() {
        if (x != 1) {
            throw new RuntimeException();
        }
        x = 0;
    }

    public static int x() {
        return x;
    }
}
