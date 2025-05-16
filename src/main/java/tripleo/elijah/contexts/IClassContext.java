package tripleo.elijah.contexts;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.*;

import java.util.Map;

public interface IClassContext extends Context {
	ClassStatement getCarrier();

	@Override
	Context getParent();

	Map<TypeName, ClassStatement> inheritance();

	@Override
	LookupResultList lookup(@NotNull String name,
							int level,
							@NotNull LookupResultList Result,
							@NotNull ISearchList alreadySearched,
							boolean one);
}
