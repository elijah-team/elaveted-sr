package tripleo.elijah_durable_elevated.stages.gen_generic;

import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_durable_elevated.stages.gen_c.*;
import tripleo.elijah_fluffy.util.*;

import java.util.*;

public enum OutputFileFactory {
	;

	private static final Map<OS_Module, GenerateFiles> mgfMap = new HashMap<>();

	@Contract("_, _, _ -> new")
	public static @NotNull GenerateFiles create(final @NotNull String lang,
			final @NotNull OutputFileFactoryParams params, final GenerateResultEnv aFileGen) {
		if (Objects.equals(lang, "c")) {
			final OS_Module mod = params.getMod();

			if (mgfMap.containsKey(mod)) {
				return mgfMap.get(mod);
			}

			final GenerateC generateC = new GenerateC(params, aFileGen);
			mgfMap.put(mod, generateC);

			return generateC;
		} else
			throw new NotImplementedException();
	}
}
