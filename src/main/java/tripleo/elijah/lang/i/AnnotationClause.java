package tripleo.elijah.lang.i;

import java.util.*;

public interface AnnotationClause {
	void add(AnnotationPart ap);

	Collection<? extends AnnotationPart> aps();
}
