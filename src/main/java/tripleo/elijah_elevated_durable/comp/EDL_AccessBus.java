package tripleo.elijah_elevated_durable.comp;

import org.jdeferred2.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah_durable_elevated.comp.i.extra.*;
import tripleo.elijah_durable_elevated.stages.gen_fn.*;
import tripleo.elijah_durable_elevated.stages.gen_generic.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.vendor.mal.*;

import java.util.*;

public class EDL_AccessBus implements AccessBus {

	public  final Old_GenerateResult gr = new Old_GenerateResult();
	private final EDL_ICompilation   _c;
	private final IPipelineAccess    _pa;
	private final @NotNull stepA_mal.MalEnv2  env;

	private final Eventual<GenerateResult> generateResultPromise = new Eventual<>();
	private final Eventual<List<EvaNode>> lgcPromise 		 = new Eventual<>();

	public EDL_AccessBus(final EDL_ICompilation aC, final IPipelineAccess aPa) {
		_c = aC;
		_pa = aPa;

		env = new stepA_mal.MalEnv2(null); // TODO what does null mean?

		generateResultPromise.register(_c.getFluffy());
		lgcPromise           .register(_c.getFluffy());
	}

	@Override
	public stepA_mal.@NotNull MalEnv2 env() {
		return env;
	}

	@Override
	public @NotNull EDL_ICompilation getCompilation() {
		return _c;
	}

	@Override
	public IPipelineAccess getPipelineAccess() {
		return _pa;
	}

	@Override
	public void resolveGenerateResult(final GenerateResult aGenerateResult) {
		generateResultPromise.resolve(aGenerateResult);
	}

	@Override
	public void resolvePipelineLogic(final PipelineLogic aPipelineLogic) {
		_pa.getPipelineLogicPromise().resolve(aPipelineLogic);
	}

	@Override
	public void subscribe_GenerateResult(final @NotNull AB_GenerateResultListener aGenerateResultListener) {
		generateResultPromise.then(aGenerateResultListener::gr_slot);
	}

	@Override
	public void subscribe_lgc(final @NotNull AB_LgcListener aLgcListener) {
		lgcPromise.then(aLgcListener::lgc_slot);
	}

	@Override
	public void subscribePipelineLogic(final DoneCallback<PipelineLogic> aPipelineLogicDoneCallback) {
		_pa.getPipelineLogicPromise().then(aPipelineLogicDoneCallback);
	}
}
