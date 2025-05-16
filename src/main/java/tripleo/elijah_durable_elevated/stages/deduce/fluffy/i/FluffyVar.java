package tripleo.elijah_durable_elevated.stages.deduce.fluffy.i;

import org.jetbrains.annotations.Nullable;
import tripleo.elijah.nextgen.composable.IComposable;
import tripleo.elijah_fluffy.diagnostic.TextLocatable;

public interface FluffyVar {
	String name();

	IComposable nameComposable();

	@Nullable
	TextLocatable nameLocatable();

	FluffyVarTarget target();
}
