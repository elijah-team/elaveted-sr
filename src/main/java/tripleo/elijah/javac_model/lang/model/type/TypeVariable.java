package tripleo.elijah.javac_model.lang.model.type;


import tripleo.elijah.javac_model.lang.model.element.*;


public interface TypeVariable extends ReferenceType {

	Element asElement();

	TypeMirror getUpperBound();

	TypeMirror getLowerBound();
}
