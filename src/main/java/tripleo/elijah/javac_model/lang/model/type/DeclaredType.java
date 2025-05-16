package tripleo.elijah.javac_model.lang.model.type;


import tripleo.elijah.javac_model.lang.model.element.*;

import java.util.*;


public interface DeclaredType extends ReferenceType {

	Element asElement();

	TypeMirror getEnclosingType();

	List<? extends TypeMirror> getTypeArguments();
}
