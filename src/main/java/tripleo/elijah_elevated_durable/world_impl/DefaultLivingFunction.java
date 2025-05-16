package tripleo.elijah_elevated_durable.world_impl;

import org.jdeferred2.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_durable_elevated.lang.impl.*;
import tripleo.elijah_durable_elevated.stages.deduce.fluffy.i.*;
import tripleo.elijah_durable_elevated.stages.gen_fn.*;
import tripleo.elijah_durable_elevated.world.i.*;
import tripleo.elijah_elevated_durable.comp.*;
import tripleo.elijah_fluffy.util.*;

public class DefaultLivingFunction implements ElevatedLivingFunction {
	private final           FunctionDef       _element;
	private final @Nullable BaseEvaFunction   _gf;
	private                 Eventual<Integer> codeCallback;
	private                 boolean           __registered;

	public DefaultLivingFunction(final @NotNull BaseEvaFunction aFunction) {
		_element = aFunction.getFD();
		_gf      = aFunction;
	}

	@Override
	public int getCode() {
		return _gf.getCode();
	}

	@Override
	public BaseFunctionDef getElement() {
		return (BaseFunctionDef) _element;
	} // ?? eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee

	@Override
	public BaseEvaFunction evaNode() {
		return _gf;
	}

	@Override
	public void codeRegistration(final LF_CodeRegistration acr) {
		if (codeCallback == null) {
			// 1. allocate
			codeCallback = new Eventual<>();

			// 2. initialize
			final EDL_ICompilation compilation = (EDL_ICompilation) _element.getContext().module().getCompilation();
			final FluffyComp       fluffy      = compilation.getFluffy();
			codeCallback.register(fluffy);

			// 3. setup
			codeCallback.then(i -> {
				final IEvaFunctionBase evaFunction = evaNode();
				evaFunction.setCode(i);
				__registered = true;
			});
		}

		// 4. trigger
		if (evaNode().getCode() == 0) {
			final EvaFunction evaFunction = (EvaFunction) evaNode();
			acr.accept(evaFunction, codeCallback);
		}
	}

	@Override
	public boolean isRegistered() {
		return __registered;
	}

	@Override
	public void listenRegister(final DoneCallback<Integer> aCodeCallback) {
		codeCallback.then(aCodeCallback);
	}
}
