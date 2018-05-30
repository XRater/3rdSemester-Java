package testCases.exceptions;

import annotations.Test;

public class WrongException {

    @Test(expected = ArithmeticException.class)
    public void failed() {
        throw new NullPointerException();
    }

}
