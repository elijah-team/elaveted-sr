package tripleo.elijah_durable_elevated.comp;

import org.jetbrains.annotations.*;
import tripleo.elijah.contexts.*;
import tripleo.elijah.g.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.world.i.*;
import tripleo.elijah_durable_elevated.contexts.*;
import tripleo.elijah_durable_elevated.lang.impl.*;
import tripleo.elijah_durable_elevated.world.i.LivingRepo;
import tripleo.elijah_elevated_durable.comp.*;
import tripleo.elijah_fluffy.util.*;

public class ModuleBuilder {
	// private final Compilation compilation;
	private final @NotNull OS_Module mod;
	private                boolean   _addToCompilation = false;
	private @Nullable      String    _fn               = null;

	public ModuleBuilder(@NotNull EDL_ICompilation aCompilation) {
//			compilation = compilation;
		mod = new OS_ModuleImpl();
		mod.setParent(aCompilation);
	}

	public @NotNull ModuleBuilder addToCompilation() {
		_addToCompilation = true;
		return this;
	}

	public OS_Module build() {
		if (_addToCompilation) {
			if (_fn == null) {
				throw new IllegalStateException("Filename not set in ModuleBuilder");
			}

			final @Nullable EDL_ICompilation compilation = (EDL_ICompilation) mod.getCompilation();
			final @NotNull LivingRepo        world       = compilation.world();
			world.addModule(mod, _fn, compilation);
		}
		return mod;
	}

	public @NotNull ModuleBuilder setContext() {
		final ModuleContext mctx = new ModuleContextImpl(mod);
		mod.setContext(mctx);
		return this;
	}

	public @NotNull ModuleBuilder withFileName(String aFn) {
		_fn = aFn;
		mod.setFileName(aFn);
		return this;
	}

	public @NotNull ModuleBuilder withPrelude(String aPrelude) {
		final Operation2<GWorldModule> prelude = mod.getCompilation().findPrelude(aPrelude);

		assert prelude.mode() == Mode.SUCCESS;

		mod.setPrelude(((WorldModule) prelude.success()).module());

		return this;
	}
}
