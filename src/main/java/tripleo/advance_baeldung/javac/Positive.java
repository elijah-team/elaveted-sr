package tripleo.advance_baeldung.javac;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.PARAMETER})
public @interface Positive {
}
