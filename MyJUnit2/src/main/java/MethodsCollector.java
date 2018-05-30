import annotations.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * This class collects methods of given class by groups.
 *
 * Collects methods for every supported annotation.
 */
@SuppressWarnings("WeakerAccess")
public class MethodsCollector {

    private final Class<?> clazz;

    private final List<Method> testCases = new ArrayList<>();
    private final List<Method> beforeClassMethods = new ArrayList<>();
    private final List<Method> afterClassMethods = new ArrayList<>();
    private final List<Method> beforeMethods = new ArrayList<>();
    private final List<Method> afterMethods = new ArrayList<>();

    /**
     * Creates new MethodCollector from the given class. Collector will group
     * methods of given class by annotation type.
     *
     * @param clazz class to collect methods from.
     */
    public MethodsCollector(final Class<?> clazz) {
        this.clazz = clazz;
        collectMethods();
    }

    /**
     * All methods with {@link Test} annotation.
     */
    public List<Method> getTestCases() {
        return testCases;
    }

    /**
     * All methods with {@link BeforeClass} annotation.
     */
    public List<Method> getBeforeClassMethods() {
        return beforeClassMethods;
    }

    /**
     * All methods with {@link AfterClass} annotation.
     */
    public List<Method> getAfterClassMethods() {
        return afterClassMethods;
    }

    /**
     * All methods with {@link Before} annotation.
     */
    public List<Method> getBeforeMethods() {
        return beforeMethods;
    }

    /**
     * All methods with {@link After} annotation.
     */
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
