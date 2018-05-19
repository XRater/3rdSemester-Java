package testCases.exceptions;

import annotations.Test;

public class UnsupposedException {

    @Test
    public void failed() {
        throw new ArithmeticException();
    }

    @Test
    public void good() {}

}
