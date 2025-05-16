package tripleo.elijah.javac_model.lang.model.type;

import tripleo.elijah.javac_model.lang.model.*;
import tripleo.elijah.javac_model.lang.model.element.*;

import java.lang.annotation.*;
import java.util.*;

public interface TypeMirror extends AnnotatedConstruct {

	TypeKind getKind();

	@Override
	int hashCode();

	@Override
	boolean equals(Object obj);

	@Override
	String toString();

	@Override
	List<? extends AnnotationMirror> getAnnotationMirrors();

	@Override
	<A extends Annotation> A getAnnotation(Class<A> annotationType);

	@Override
	<A extends Annotation> A[] getAnnotationsByType(Class<A> annotationType);

	<R, P> R accept(TypeVisitor<R, P> v, P p);
}
