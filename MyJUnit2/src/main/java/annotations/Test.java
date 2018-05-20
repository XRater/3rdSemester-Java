package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark methods, that must be invoked as tests.
 *
 * If {@link Test#ignore()} argument is set and equals to something else then "NO_IGNORE",
 * method invocation will be skipped with reason written in {@link Test#ignore()} argument.
 *
 * If {@link Test#expected()} argument is set to any exception test invocation will expect
 * {@link Test#expected()} exception to be thrown. If no exception was thrown, or exception
 * class was not the target one, test will be counted as failed.
 *
 * If no {@link Test#expected()} was provided test will be counted as failed in case if
 * any exception was thrown.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Test {

    String NO_IGNORE = "NO_IGNORE";

    String ignore() default NO_IGNORE;

    Class<? extends Throwable> expected() default NONE.class;

    class NONE extends Throwable {}
}
