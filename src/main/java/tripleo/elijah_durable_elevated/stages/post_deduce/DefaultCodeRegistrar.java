package tripleo.elijah_durable_elevated.stages.post_deduce;

import tripleo.elijah_elevated_durable.comp.EDL_ICompilation;
import tripleo.elijah_durable_elevated.stages.gen_fn.*;
import tripleo.elijah_durable_elevated.stages.gen_generic.ICodeRegistrar;
import tripleo.elijah_durable_elevated.world.i.LivingRepo;

public class DefaultCodeRegistrar implements ICodeRegistrar {
	private final EDL_ICompilation compilation;

	public DefaultCodeRegistrar(final EDL_ICompilation aC) {
		compilation = aC;
	}

	private LivingRepo getLivingRepo() {
		return compilation.world();
	}

	@Override
	public void registerClass(final EvaClass aClass) {
		getLivingRepo().addClass(aClass, LivingRepo.Add.MAIN_CLASS);
	}

	@Override
	public void registerClass1(final EvaClass aClass) {
		getLivingRepo().addClass(aClass, LivingRepo.Add.NONE);
	}

	@Override
	public void registerFunction(final BaseEvaFunction aFunction) {
		getLivingRepo().addFunction(aFunction, LivingRepo.Add.MAIN_FUNCTION);
	}

	@Override
	public void registerFunction1(final BaseEvaFunction aFunction) {
		getLivingRepo().addFunction(aFunction, LivingRepo.Add.NONE);
	}

	@Override
	public void registerNamespace(final EvaNamespace aNamespace) {
		getLivingRepo().addNamespace(aNamespace, LivingRepo.Add.NONE);
	}
}
