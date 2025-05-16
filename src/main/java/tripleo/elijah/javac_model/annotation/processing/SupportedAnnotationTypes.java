package tripleo.elijah.javac_model.annotation.processing;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface SupportedAnnotationTypes {
	String[] value();
}
