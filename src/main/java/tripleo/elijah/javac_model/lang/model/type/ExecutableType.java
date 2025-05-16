package tripleo.elijah.javac_model.lang.model.type;


import java.util.*;

public interface ExecutableType extends TypeMirror {

	List<? extends TypeVariable> getTypeVariables();

	TypeMirror getReturnType();

	List<? extends TypeMirror> getParameterTypes();

	TypeMirror getReceiverType();

	List<? extends TypeMirror> getThrownTypes();
}
