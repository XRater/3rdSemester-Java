package testCases.exceptions;

import annotations.Test;

public class MoreFailing {

    @Test
    public void failedNone() {
        throw new NullPointerException();
    }

    @Test(expected = ArithmeticException.class)
    public void failedWrong() {
        throw new NullPointerException();
    }
}
