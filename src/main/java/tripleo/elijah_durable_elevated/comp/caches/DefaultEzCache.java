package tripleo.elijah_durable_elevated.comp.caches;

import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah_elevated_durable.comp.*;

import java.util.*;

public class DefaultEzCache implements EzCache {
	final         Map<String, CompilerInstructions> fn2ci = new HashMap<>();
	private final EDL_ICompilation                  compilation;

	public DefaultEzCache(final EDL_ICompilation aCompilation) {
		compilation = aCompilation;
	}

	@Override
	public Optional<CompilerInstructions> get(final String absolutePath) {
		if (fn2ci.containsKey(absolutePath)) {
			return Optional.of(fn2ci.get(absolutePath));
		}

		return Optional.empty();
	}

	@Override
	public void put(final EzSpec aSpec, final String aAbsolutePath, final CompilerInstructions aCompilerInstructions) {
		fn2ci.put(aAbsolutePath, aCompilerInstructions);
	}

	@Override public Compilation getCompilation() {
		return compilation;
	}
}
