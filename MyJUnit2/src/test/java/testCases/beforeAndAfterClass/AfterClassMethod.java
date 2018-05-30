package testCases.beforeAndAfterClass;

import annotations.AfterClass;
import annotations.Test;

public class AfterClassMethod {

    private static int x = 0;

    @AfterClass
    public void tearDown() {
        x = 1;
    }

    @Test
    public void test() {
        if (x != 0) {
            throw new RuntimeException();
        }
    }

    public static int x() {
        return x;
    }
}
