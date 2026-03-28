package tripleo.elijah_elevated_durable.world_impl;

import org.jdeferred2.impl.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.comp.nextgen.inputtree.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.world.i.*;
import tripleo.elijah_durable_elevated.nextgen.inputtree.*;
import tripleo.elijah_durable_elevated.stages.gen_c.*;
import tripleo.elijah_durable_elevated.stages.gen_generic.*;
import tripleo.elijah_durable_elevated.stages.inter.*;
import tripleo.elijah_durable_elevated.world.i.LivingRepo;
import tripleo.elijah_elevated_durable.backbone.*;
import tripleo.elijah_elevated_durable.comp_notation.*;
import tripleo.elijah_elevated_durable.lang_model.*;
import tripleo.elijah_fluffy.util.*;

public class DefaultWorldModule implements WorldModule {
	private final OS_Module                                     langModule;
	// get rid of this too
	private final ModuleThing                                   thing;
	// get rid of these two
	private final Eventual<GN_PL_Run2.GenerateFunctionsRequest> erq                = new Eventual<>();
	private final DeferredObject<GenerateC, Void, Void>         _generateCDeferred = new DeferredObject<>();
	private final LivingRepo                                    livingRepo;
	// prob should be triple
	@SuppressWarnings("FieldCanBeLocal")//
	private final DoubleLatch<CompilationEnclosure>             mid;
	private       EIT_ModuleInput                               eitInput;

	public DefaultWorldModule(final OS_Module aMod, final @NotNull CompilationEnclosure aCompilationEnclosure) {
		this.langModule = aMod;
		this.thing      = aCompilationEnclosure.addModuleThing(langModule);
		this.livingRepo = aCompilationEnclosure.getCompilation().world();
		this.mid        = new DoubleLatch<@NotNull CompilationEnclosure>(aCompilationEnclosure2 -> eitInput = new EIT_ModuleInputImpl(langModule, aCompilationEnclosure2.getCompilation()));
	}

	@Override
	public EIT_ModuleInput getEITInput() {
		return this.eitInput;
	}

	@Override
	public Eventual<GN_PL_Run2.GenerateFunctionsRequest> getErq() {
		return erq;
	}

	@Override
	public OS_Module module() {
		return langModule;
	}

	@Override
	public DeferredObject<GenerateC, Void, Void> generateCDeferred() {
		return this._generateCDeferred;
	}

	@Override
	public void addUnderstanding(final EN_Understanding aENUnderstanding) {
		NotImplementedException.raise_stop();
	}

	public void setRq(final GN_PL_Run2.GenerateFunctionsRequest aRq) {
		erq.resolve(aRq);
	}

	public ModuleThing thing() {
		return thing;
	}

	@Override
	public String toString() {
		return "DefaultWorldModule{%s}".formatted(langModule.getFileName());
	}

	public LivingRepo getLivingRepo() {
		return livingRepo;
	}
}
