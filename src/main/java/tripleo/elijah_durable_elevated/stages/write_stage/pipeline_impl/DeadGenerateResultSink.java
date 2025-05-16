package tripleo.elijah_durable_elevated.stages.write_stage.pipeline_impl;

import org.jetbrains.annotations.*;
import tripleo.elijah.g.*;
import tripleo.elijah_durable_elevated.stages.gen_c.*;
import tripleo.elijah_durable_elevated.stages.gen_fn.*;
import tripleo.elijah_durable_elevated.stages.gen_generic.*;
import tripleo.elijah_durable_elevated.stages.gen_generic.pipeline_impl.*;
import tripleo.elijah_durable_elevated.world.i.*;
import tripleo.elijah_fluffy.util.*;

import java.util.*;

/**
 * All methods implemented here should throw {@code UnintendedUseException}
 */
public abstract class DeadGenerateResultSink implements GenerateResultSink {
	@Override
	public void add(final GRS_Addable node) {
		throw new UnintendedUseException();
	}

	@Override
	public void addFunction(final BaseEvaFunction aGf, final List<C2C_Result> aRs, final GenerateFiles aGenerateFiles) {
		throw new UnintendedUseException();
	}

	@Override
	public void additional(final GenerateResult aGenerateResult) {
		throw new UnintendedUseException();
	}

	@Override
	public @Nullable ElevatedLivingClass getLivingClassForEva(final GEvaClass aEvaClass) {
		throw new UnintendedUseException();
	}

	@Override
	public @Nullable LivingNamespace getLivingNamespaceForEva(final GEvaNamespace aEvaClass) {
		throw new UnintendedUseException();
	}

	@Override
	public GPipelineAccess pa() {
		throw new UnintendedUseException();
	}
}
