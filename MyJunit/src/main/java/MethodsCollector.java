import annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MethodsCollector {

    private final Class<?> clazz;

    private final List<Method> testCases = new ArrayList<>();

    public MethodsCollector(final Class<?> clazz) {
        this.clazz = clazz;
        collectMethods();
    }

    public List<Method> getTestCases() {
        return testCases;
    }

    private void collectMethods() {
        for (final Method method : clazz.getDeclaredMethods()) {
            if (method.getAnnotation(Test.class) != null) {
                testCases.add(method);
            }
        }
    }

}
