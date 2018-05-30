package testCases.beforeAndAfter;

import annotations.After;
import annotations.Test;

public class AfterMethod {

    private static int x = 1;

    @After
    public void after() {
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

    @Test
    public void test3() {
        if (x != 1) {
            throw new RuntimeException();
        }
        x = 0;
    }

    public static int x() {
        return x;
    }
}
