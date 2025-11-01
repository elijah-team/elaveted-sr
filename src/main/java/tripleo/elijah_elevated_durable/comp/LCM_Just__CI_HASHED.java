package tripleo.elijah_elevated_durable.comp;

import tripleo.elijah.compiler_model.*;
import tripleo.elijah_fluffy.util.*;

public record LCM_Just__CI_HASHED(
		tripleo.elijah.comp.specs.EzSpec spec,
		tripleo.elijah_fluffy.util.Operation<String> hash,
		tripleo.graph.CK_SourceFile sourceFile,
		EDL_Compilation compilation
) implements LCM_Just {
	public static LCM_Just__CI_HASHED create(final CM_Ez t, final EDL_Compilation aCompilation) {
		return new LCM_Just__CI_HASHED(t.spec(), Operation.success(t.hash()), t.sourceFile(), aCompilation);
	}
}
