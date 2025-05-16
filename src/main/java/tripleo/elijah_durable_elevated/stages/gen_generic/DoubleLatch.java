package tripleo.elijah_durable_elevated.stages.gen_generic;

import org.jetbrains.annotations.*;
import tripleo.elijah_fluffy.util.*;

import java.util.function.*;

public class DoubleLatch<T> {
	private final @NotNull Consumer<T> action;
	private boolean simple;
	private T tt;

	// private IincInsnNode action;

	@Contract(pure = true)
	public DoubleLatch(final @NotNull Consumer<T> aAction) {
		action = aAction;
	}

	public void notifyData(T att) {
		tt = att;
		if (simple && tt != null) {
			action.accept(tt);
		}
	}

	public void notifyLatch(final Ok ignored) {
		simple = true;
		if (simple && tt != null) {
			action.accept(tt);
		}
	}
}
