package testCases.other;

import annotations.Test;

public class Modifiers {

    @Test
    public void publicMethod() {}

    @Test
    void defaultMethod() {}

    @Test
    protected void protectedMethod() {}

    @Test
    private void privateMethod() {}

}
