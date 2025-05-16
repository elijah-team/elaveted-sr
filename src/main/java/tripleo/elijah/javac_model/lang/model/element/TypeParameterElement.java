package tripleo.elijah.javac_model.lang.model.element;

import tripleo.elijah.javac_model.lang.model.type.*;

import java.util.*;

public interface TypeParameterElement extends Element {
	@Override
	TypeMirror asType();

	@Override
	Element getEnclosingElement();

	Element getGenericElement();

	List<? extends TypeMirror> getBounds();
}
