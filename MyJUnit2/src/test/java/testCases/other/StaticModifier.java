package testCases.other;

import annotations.Test;

public class StaticModifier {

    @Test
    public static void publicMethod() {}

    @Test
    static void defaultMethod() {}

    @Test
    protected static void protectedMethod() {}

    @Test
    private static void privateMethod() {}

}
