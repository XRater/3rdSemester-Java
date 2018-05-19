import annotations.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class MethodsCollector {

    private final Class<?> clazz;

    private final List<Method> testCases = new ArrayList<>();
    private final List<Method> beforeClassMethods = new ArrayList<>();
    private final List<Method> afterClassMethods = new ArrayList<>();
    private final List<Method> beforeMethods = new ArrayList<>();
    private final List<Method> afterMethods = new ArrayList<>();

    public MethodsCollector(final Class<?> clazz) {
        this.clazz = clazz;
        collectMethods();
    }

    public List<Method> getTestCases() {
        return testCases;
    }

    public List<Method> getBeforeClassMethods() {
        return beforeClassMethods;
    }

    public List<Method> getAfterClassMethods() {
        return afterClassMethods;
    }

    public List<Method> getBeforeMethods() {
        return beforeMethods;
    }

    public List<Method> getAfterMethods() {
        return afterMethods;
    }

    private void collectMethods() {
        for (final Method method : clazz.getDeclaredMethods()) {
            if (method.getAnnotation(Test.class) != null) {
                testCases.add(method);
            }
            if (method.getAnnotation(BeforeClass.class) != null) {
                beforeClassMethods.add(method);
            }
            if (method.getAnnotation(AfterClass.class) != null) {
                afterClassMethods.add(method);
            }
            if (method.getAnnotation(Before.class) != null) {
                beforeMethods.add(method);
            }
            if (method.getAnnotation(After.class) != null) {
                afterMethods.add(method);
            }
        }
    }

}
