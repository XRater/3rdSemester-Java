import annotations.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyJUnitLauncher {

    public static void testClass(final Class<?> clazz) throws InvocationTargetException, IllegalAccessException,
            NoSuchMethodException, InstantiationException {
        run(clazz);
    }

    private static void run(final Class<?> clazz) throws InvocationTargetException, IllegalAccessException,
            InstantiationException, NoSuchMethodException {
        final Constructor<?> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        final Object object = constructor.newInstance();

        final Logger logger = new Logger(clazz, object);
        logger.start();

        final MethodsCollector collector = new MethodsCollector(clazz);
        for (final Method method : collector.getTestCases())  {
            final String ignore = method.getAnnotation(Test.class).ignore();
            if (!ignore.equals(Test.NO_IGNORE)) {
                logger.logTestSkipped(method, ignore);
                continue;
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
        }

        logger.finish();
    }

    public static void main(final String[] args) throws InvocationTargetException, IllegalAccessException,
            NoSuchMethodException, InstantiationException {
        run(Hello.class);
    }
}
