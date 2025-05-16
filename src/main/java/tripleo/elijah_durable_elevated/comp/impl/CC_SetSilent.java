package tripleo.elijah_durable_elevated.comp.impl;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;

public class CC_SetSilent implements CompilationChange {
	private final boolean flag;

	public CC_SetSilent(final boolean aB) {
		flag = aB;
	}

	@Override
	public void apply(final @NotNull Compilation c) {
		c.cfg().setSilent(flag);
	}
}
