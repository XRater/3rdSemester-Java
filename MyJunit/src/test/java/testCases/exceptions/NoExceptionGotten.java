package testCases.exceptions;

import annotations.Test;

public class NoExceptionGotten {

    @Test(expected = ArithmeticException.class)
    public void empty() {}

}
