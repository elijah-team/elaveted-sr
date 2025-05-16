package tripleo.elijah.javac_model.lang.model.type;

import java.util.*;

public interface UnionType extends TypeMirror {

	List<? extends TypeMirror> getAlternatives();
}
