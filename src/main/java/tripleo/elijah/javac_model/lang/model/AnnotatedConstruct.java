package tripleo.elijah.javac_model.lang.model;

import tripleo.elijah.javac_model.lang.model.element.*;

import java.lang.annotation.*;
import java.util.*;

public interface AnnotatedConstruct {
	List<? extends AnnotationMirror> getAnnotationMirrors();

	<A extends Annotation> A getAnnotation(Class<A> annotationType);

	<A extends Annotation> A[] getAnnotationsByType(Class<A> annotationType);
}
