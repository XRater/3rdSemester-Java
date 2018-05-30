package testCases.beforeAndAfterClass;

import annotations.Test;
import annotations.BeforeClass;

public class BeforeClassMethod {

    private static int x = 0;

    @BeforeClass
    public void setUp() {
        x = 1;
    }

    @Test
    public void test() {
        if (x != 1) {
            throw new RuntimeException();
        }
    }

    public static int x() {
        return x;
    }
}
