import annotations.Test;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The class to run all methods marked with {@link Test} annotation in the target class.
 *
 * Every testing information will be printed to System.out.
 *
 * For more information see {@link annotations.After}, {@link annotations.Before},
 * {@link annotations.AfterClass} and {@link annotations.BeforeClass} annotations.
 */
@SuppressWarnings("WeakerAccess")
public class MyJUnitLauncher {

    public static void main(final String[] args) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (args.length != 1) {
            System.out.println("Give path to directory with tests as argument");
            return;
        }

        testPath(Paths.get(args[0]));
    }

    private static void testPath(final Path path) throws IOException, ClassNotFoundException, NoSuchMethodException,
            InstantiationException, IllegalAccessException, InvocationTargetException {
        for (final Path subPath : Files.walk(path).collect(Collectors.toList())) {
            final String name = subPath.getFileName().toString();
            if (name.endsWith(".class")) {
                @NotNull final URL url = new URL("file:" + subPath.getParent() + "/");
                @NotNull final ClassLoader classLoader = new URLClassLoader(new URL[]{url});
                final Class<?> clazz =  classLoader.loadClass(name.substring(0, name.length() - ".class".length()));
                testClass(clazz);
            }
        }
    }

    /**
     * Processes testing of given class.
     *
     * @param clazz class to test.
     * @throws InvocationTargetException if any of {@link annotations.After} or {@link annotations.AfterClass} methods
     * or class constructor had thrown an exception.
     * @throws IllegalAccessException if access to constructor or method was denied.
     * @throws NoSuchMethodException if class does not have default constructor.
     * @throws InstantiationException if class was abstract.
     */
    public static void testClass(final Class<?> clazz) throws
            NoSuchMethodException, InstantiationException, InvocationTargetException, IllegalAccessException {
        run(clazz);
    }

    private static void run(final Class<?> clazz) throws IllegalAccessException,
            InstantiationException, NoSuchMethodException, InvocationTargetException {
        final Constructor<?> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        final Object object = constructor.newInstance();

        final MyJUnitLogger logger = new MyJUnitLogger(clazz, object);
        logger.start();

        final MethodsCollector collector = new MethodsCollector(clazz);

        try {
            processBefore(object, logger, collector);
        } catch (final AbortException e) {
            logger.finish();
            return;
        }
        processTests(object, logger, collector);
        processAfter(object, logger, collector);

        logger.finish();
    }

    @SuppressWarnings("unused")
    private static void processAfter(final Object object, final MyJUnitLogger logger, final MethodsCollector collector) throws InvocationTargetException {
        for (final Method method : collector.getAfterClassMethods()) {
            method.setAccessible(true);
            try {
                method.invoke(object);
            } catch (final IllegalAccessException e) {
                throw new RuntimeException();
            }
        }
    }

    private static void processBefore(final Object object, final MyJUnitLogger logger, final MethodsCollector collector) throws IllegalAccessException, AbortException {
        for (final Method method : collector.getBeforeClassMethods()) {
            method.setAccessible(true);
            try {
                method.invoke(object);
            } catch (final InvocationTargetException inv) {
                logger.logSetUpFailed(method, inv.getCause());
                throw new AbortException();
            }
        }
    }

    private static void processBeforeEach(final Object object, final MyJUnitLogger logger, final List<Method> methods) throws IllegalAccessException, AbortException {
        for (final Method method : methods) {
            method.setAccessible(true);
            try {
                method.invoke(object);
            } catch (final InvocationTargetException inv) {
                logger.logFailedMethodSetUp(method, inv.getCause());
                throw new AbortException();
            }
        }
    }

    @SuppressWarnings("unused")
    private static void processAfterEach(final Object object, final MyJUnitLogger logger, final List<Method> methods) throws IllegalAccessException, InvocationTargetException {
        for (final Method method : methods) {
            method.setAccessible(true);
            method.invoke(object);
        }
    }

    private static void processTests(final Object object, final MyJUnitLogger logger, final MethodsCollector collector) throws IllegalAccessException, InvocationTargetException {
        final List<Method> beforeEach = collector.getBeforeMethods();
        final List<Method> afterEach = collector.getAfterMethods();
        for (final Method method : collector.getTestCases())  {
            final String ignore = method.getAnnotation(Test.class).ignore();
            if (!ignore.equals(Test.NO_IGNORE)) {
                logger.logTestSkipped(method, ignore);
                continue;
            }

            try {
                processBeforeEach(object, logger, beforeEach);
            } catch (final AbortException e) {
                continue; // skip the test
            }

            logger.logMethodStart(method);

            method.setAccessible(true);
            Throwable t = null;
            final Class<? extends Throwable> expected =
                    method.getAnnotation(Test.class).expected();
            try {
                method.invoke(object);
            } catch (final InvocationTargetException inv) {
                t = inv.getTargetException();
            }

            if (t != null ? t.getClass().equals(expected) : expected == Test.NONE.class) {
                logger.logTestPassed(method);
            } else {
                logger.logTestFailed(method, expected, t == null ? null : t.getClass());
            }

            processAfterEach(object, logger, afterEach);
        }
    }

    /**
     * This exception is used when any exception occurred during Before/After methods.
     */
    private static class AbortException extends Throwable {
    }
}
