package tripleo.elijah.lang.i;

import java.util.*;

public interface ClassInheritance {
	void add(TypeName tn);

	void addAll(Collection<TypeName> tns);

	List<TypeName> tns();
}
