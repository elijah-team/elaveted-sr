package tripleo.elijah.lang.i;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah_elevated_durable.lang_model.*;

public interface Context {
	void addName(EN_Name aName);

	@NotNull <T extends Compilation> T compilation();

	@Nullable
	Context getParent();

	LookupResultList lookup(@NotNull String name);

	LookupResultList lookup(String name, int level, LookupResultList Result, ISearchList alreadySearched, boolean one);

	@NotNull
	OS_Module module();
}
