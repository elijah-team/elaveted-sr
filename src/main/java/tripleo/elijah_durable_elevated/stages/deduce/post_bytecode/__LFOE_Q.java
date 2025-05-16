package tripleo.elijah_durable_elevated.stages.deduce.post_bytecode;

import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_durable_elevated.stages.deduce.*;
import tripleo.elijah_durable_elevated.stages.gen_fn.*;
import tripleo.elijah_durable_elevated.stages.gen_generic.*;
import tripleo.elijah_durable_elevated.work.*;
import tripleo.elijah_fluffy.util.*;

import java.util.function.*;

public class __LFOE_Q implements _LFOE_Q {
	private final @NotNull GenerateFunctions generateFunctions;
	private final          WorkList          wl;
	private final @NotNull DeduceTypes2      deduceTypes2;

	public __LFOE_Q(final @NotNull DeduceTypes2 aDeduceTypes2) {
		this(null, null, aDeduceTypes2);
	}

	public __LFOE_Q(WorkManager aWorkManager, WorkList aWorkList, final @NotNull DeduceTypes2 aDeduceTypes2) {
		this.deduceTypes2      = aDeduceTypes2;
		this.generateFunctions = aDeduceTypes2.phase.getGeneratePhase().getGenerateFunctions(aDeduceTypes2.module);
		if (aWorkList == null) {
			wl = new EDP_WorkList();
		} else {
			wl = aWorkList;
		}
	}

	@Override
	public void enqueue_ctor(final GenerateFunctions generateFunctions1,
							 final @NotNull FunctionInvocation fi,
							 final IdentExpression aConstructorName) {
		assert generateFunctions1 == generateFunctions; // eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
		final WlGenerateCtor wlgf = _inj().new_WlGenerateCtor(generateFunctions,
															  fi,
															  aConstructorName,
															  deduceTypes2.phase.getCodeRegistrar());
		wl.addJob(wlgf);
	}

	private DeduceTypes2Injector _inj() {
		return deduceTypes2._inj();
	}

	@Override
	public void enqueue_default_ctor(final GenerateFunctions generateFunctions1,
									 final @NotNull FunctionInvocation fi,
									 final ICodeRegistrar codeRegistrar,
									 final Consumer<Eventual<BaseEvaFunction>> cef) {
		assert generateFunctions1 == generateFunctions;
		final WlGenerateDefaultCtor wlgf = _inj().new_WlGenerateDefaultCtor(generateFunctions,
																			fi,
																			deduceTypes2.creationContext(),
																			codeRegistrar);

		Eventual<BaseEvaFunction> x = wlgf.getGenerated();
		if (cef != null) cef.accept(x);

		wl.addJob(wlgf);
	}

	@Override
	public void enqueue_function(final GenerateFunctions generateFunctions1,
								 final @NotNull FunctionInvocation fi,
								 final ICodeRegistrar cr) {
		// assert generateFunctions1 == generateFunctions;
		final WlGenerateFunction wlgf = _inj().new_WlGenerateFunction(generateFunctions, fi, cr);
		wl.addJob(wlgf);
	}

	@Override
	public void enqueue_function(final @NotNull Supplier<GenerateFunctions> som,
								 final @NotNull FunctionInvocation aFi,
								 final ICodeRegistrar cr) {
		final WlGenerateFunction wlgf = _inj().new_WlGenerateFunction(som.get(), aFi, cr);
		wl.addJob(wlgf);
	}

	@Override
	public void enqueue_namespace(final @NotNull Supplier<GenerateFunctions> som,
								  final @NotNull NamespaceInvocation aNsi,
								  final DeducePhase.GeneratedClasses aGeneratedClasses,
								  final ICodeRegistrar aCr) {
		wl.addJob(_inj().new_WlGenerateNamespace(som.get(), aNsi, aGeneratedClasses, aCr));
	}

	@Override
	public void enqueue_default_ctor(final GenerateFunctions generateFunctions1,
									 final @NotNull FunctionInvocation fi,
									 final Consumer<Eventual<BaseEvaFunction>> cef) {
		assert generateFunctions1 == generateFunctions; // eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
		final ICodeRegistrar codeRegistrar = deduceTypes2._phase().getCodeRegistrar();
		enqueue_default_ctor(generateFunctions1, fi, codeRegistrar, cef);
	}
}
