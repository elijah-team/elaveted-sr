package tripleo.elijah.lang.i;

import org.jetbrains.annotations.*;
import tripleo.elijah.contexts.*;

import java.util.*;
import java.util.function.*;

public interface LookupResultList {
	void add(String name, int level, OS_Element element, Context aContext);

	void add(String name, int level, OS_Element element, Context aContext, ContextInfo aImportInfo);

	@Nullable
	OS_Element chooseBest(List<Predicate<OS_Element>> l);

	List<LookupResult> getMaxScoredResults(List<Predicate<OS_Element>> l);

	List<LookupResult> results();
}
