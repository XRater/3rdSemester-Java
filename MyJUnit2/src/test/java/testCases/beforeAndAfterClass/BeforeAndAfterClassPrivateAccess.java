package testCases.beforeAndAfterClass;

import annotations.AfterClass;
import annotations.BeforeClass;
import annotations.Test;

public class BeforeAndAfterClassPrivateAccess {

    private static int x = 0;

    @BeforeClass
    private void before() {
        x = 1;
    }

    @Test
    public void test() {
        if (x != 1) {
            throw new RuntimeException();
        }
    }

    @AfterClass
    private void after() {
        x = 2;
    }

    public int x() { return x; }
}
