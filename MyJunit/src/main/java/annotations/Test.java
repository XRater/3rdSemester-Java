package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Test {

    String NO_IGNORE = "NO_IGNORE";

    String ignore() default NO_IGNORE;

    Class<? extends Throwable> expected() default NONE.class;

    class NONE extends Throwable {}
}
