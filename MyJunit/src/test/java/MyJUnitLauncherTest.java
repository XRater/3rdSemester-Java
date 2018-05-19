import org.junit.jupiter.api.Test;
import testCases.exceptions.*;
import testCases.base.*;
import testCases.other.Modifiers;
import testCases.other.PrivateConstructor;
import testCases.other.StaticModifier;
import testCases.skip.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;

class MyJUnitLauncherTest {

    private static final Class<? extends Throwable> none = annotations.Test.NONE.class;

    @Test
    void testEmpty() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException,
            InstantiationException {
        GeneralTest.testClass(Empty.class,
                new TestResults(Empty.class.getName(), Collections.emptyMap(),
                        Collections.emptyMap(), Collections.emptySet()));
    }

    @Test
    void testOneTest() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException,
            InstantiationException {
        GeneralTest.testClass(OneTest.class,
                new TestResults(OneTest.class.getName(), Collections.emptyMap(),
                        Collections.emptyMap(), Collections.singleton("bar")));
    }

    @Test
    void testMixedMethods() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException,
            InstantiationException {
        GeneralTest.testClass(MixedMethods.class,
                new TestResults(MixedMethods.class.getName(), Collections.emptyMap(),
                        Collections.emptyMap(), Utils.set("foo", "bar")));
    }

    @Test
    void testTwoTests() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException,
            InstantiationException {
        GeneralTest.testClass(TwoTests.class,
                new TestResults(TwoTests.class.getName(), Collections.emptyMap(),
                        Collections.emptyMap(), Utils.set("foo", "bar")));
    }

    @Test
    void testWithoutTestAnnotations() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        GeneralTest.testClass(WithoutTestAnnotations.class,
                new TestResults(WithoutTestAnnotations.class.getName(),
                        Collections.emptyMap(), Collections.emptyMap(),
                        Collections.emptySet()));
    }

    @Test
    void testStaticModifier() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException,
            InstantiationException {
        GeneralTest.testClass(StaticModifier.class,
                new TestResults(StaticModifier.class.getName(),
                        Collections.emptyMap(), Collections.emptyMap(),
                        Utils.set("publicMethod", "protectedMethod",
                                "privateMethod", "defaultMethod")));
    }

    @Test
    void testModifiers() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException,
            InstantiationException {
        GeneralTest.testClass(Modifiers.class,
                new TestResults(Modifiers.class.getName(),
                        Collections.emptyMap(), Collections.emptyMap(),
                        Utils.set("publicMethod", "protectedMethod",
                                "privateMethod", "defaultMethod")));
    }

    @Test
    void testPrivateConstructor() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException,
            InstantiationException {
        GeneralTest.testClass(PrivateConstructor.class,
                new TestResults(PrivateConstructor.class.getName(),
                        Collections.emptyMap(), Collections.emptyMap(),
                        Utils.set("test")));
    }

    @Test
    void testUnsupposedException() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException,
            InstantiationException {
        GeneralTest.testClass(UnsupposedException.class,
                new TestResults(UnsupposedException.class.getName(),
                        Utils.makeFailedMap("failed", none, ArithmeticException.class),
                        Collections.emptyMap(), Utils.set("good")));
    }

    @Test
    void testWrongException() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException,
            InstantiationException {
        GeneralTest.testClass(WrongException.class,
                new TestResults(WrongException.class.getName(),
                        Utils.makeFailedMap("failed", ArithmeticException.class, NullPointerException.class),
                        Collections.emptyMap(), Collections.emptySet()));
    }

    @Test
    void testCorrectException() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException,
            InstantiationException {
        GeneralTest.testClass(CorrectException.class,
                new TestResults(CorrectException.class.getName(), Collections.emptyMap(),
                        Collections.emptyMap(), Utils.set("good")));
    }

    @Test
    void testMoreFailing() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException,
            InstantiationException {
        GeneralTest.testClass(MoreFailing.class,
                new TestResults(MoreFailing.class.getName(),
                        Utils.makeFailedMap("failedNone", none, NullPointerException.class,
                                "failedWrong", ArithmeticException.class, NullPointerException.class),
                        Collections.emptyMap(), Collections.emptySet()));
    }

    @Test
    void testNoExceptionGotten() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException,
            InstantiationException {
        GeneralTest.testClass(NoExceptionGotten.class,
                new TestResults(NoExceptionGotten.class.getName(),
                        Utils.makeFailedMap("empty", ArithmeticException.class, null),
                        Collections.emptyMap(), Collections.emptySet()));
    }

    @Test
    void testTestIgnoreOne() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException,
            InstantiationException {
        GeneralTest.testClass(IgnoreOne.class,
                new TestResults(IgnoreOne.class.getName(),
                        Collections.emptyMap(),
                        Utils.makeSkippedMap("skipTest", "reason"),
                        Collections.emptySet()));
    }

    @Test
    void testTestIgnoreEmpty() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException,
            InstantiationException {
        GeneralTest.testClass(IgnoreEmpty.class,
                new TestResults(IgnoreEmpty.class.getName(),
                        Collections.emptyMap(),
                        Utils.makeSkippedMap("skipTest", ""),
                        Collections.emptySet()));
    }

    @Test
    void testTestIgnoreTwo() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException,
            InstantiationException {
        GeneralTest.testClass(IgnoreTwo.class,
                new TestResults(IgnoreTwo.class.getName(),
                        Collections.emptyMap(),
                        Utils.makeSkippedMap("skipTest2", "reason2", "skipTest1", "reason1"),
                        Collections.emptySet()));
    }

    @Test
    void testIgnoreWithRegular() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException,
            InstantiationException {
        GeneralTest.testClass(IgnoreWithRegular.class,
                new TestResults(IgnoreWithRegular.class.getName(),
                        Collections.emptyMap(),
                        Utils.makeSkippedMap("skipTest", "reason"),
                        Utils.set("passedTest")));
    }

    @Test
    void testIgnoreWithFailed() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException,
            InstantiationException {
        GeneralTest.testClass(IgnoreWithFailed.class,
                new TestResults(IgnoreWithFailed.class.getName(),
                        Utils.makeFailedMap("failedTest", RuntimeException.class, null),
                        Utils.makeSkippedMap("skipTest", "reason"),
                        Collections.emptySet()));
    }

    @Test
    void testIgnoreFullMix() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException,
            InstantiationException {
        GeneralTest.testClass(IgnoreFullMix.class,
                new TestResults(IgnoreFullMix.class.getName(),
                        Utils.makeFailedMap("failedTest", RuntimeException.class, null),
                        Utils.makeSkippedMap("skipTest", "reason"),
                        Utils.set("passedTest")));
    }

}