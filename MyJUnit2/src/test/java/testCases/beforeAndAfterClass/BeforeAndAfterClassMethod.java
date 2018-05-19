package testCases.beforeAndAfterClass;

import annotations.AfterClass;
import annotations.BeforeClass;
import annotations.Test;

public class BeforeAndAfterClassMethod {

    private static int x = 0;

    @BeforeClass
    public void setUp() {
        x = 2;
    }

    @AfterClass
    public void tearDown() {
        x = 1;
    }

    @Test
    public void test() {
        if (x != 2) {
            throw new RuntimeException();
        }
    }

    public static int x() {
        return x;
    }
}
