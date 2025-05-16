package tripleo.elijah_durable_elevated.stages.deduce;

import org.jetbrains.annotations.*;

import java.util.function.*;

public record RegisterClassInvocation_env(@NotNull ClassInvocation classInvocation,
										  @NotNull Supplier<DeduceTypes2> deduceTypes2Supplier,
										  @NotNull Supplier<DeducePhase> deducePhaseSupplier) {


	@Override
	public String toString() {
		return "RegisterClassInvocation_env[" +
				"classInvocation=" + classInvocation + ", " +
				"deduceTypes2Supplier=" + deduceTypes2Supplier + ", " +
				"deducePhaseSupplier=" + deducePhaseSupplier + ']';
	}
}
