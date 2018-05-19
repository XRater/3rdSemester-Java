package testCases.exceptions;

import annotations.Test;

public class CorrectException {

    @Test(expected = ArithmeticException.class)
    public void good() {
        throw new ArithmeticException();
    }

}
