package tripleo.elijah.javac_model.lang.model.element;

import tripleo.elijah.javac_model.lang.model.type.*;

import java.util.*;

public interface AnnotationMirror {

	DeclaredType getAnnotationType();

	Map<? extends ExecutableElement, ? extends AnnotationValue> getElementValues();
}
