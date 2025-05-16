package tripleo.elijah_elevated_durable.comp_queries;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.queries.*;

public class QSEZ_Reasonings {
	@Contract("_ -> new")
	public static @NotNull QSEZ_Reasoning create(@NotNull Ty o) {
		switch (o) {
		case NONE -> {
			return new _QSEZ_Reasoning__NONE();
		}
		case EID -> {
			return new _QSEZ_Reasoning__EID();
		}
		default -> throw new IllegalStateException("Unexpected value: " + o);
		}
	}

	public enum Ty {NONE,EID}

	private static class _QSEZ_Reasoning__NONE implements QSEZ_Reasoning {
	}

	private static class _QSEZ_Reasoning__EID implements QSEZ_Reasoning {
	}
}
