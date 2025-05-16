package tripleo.elijah_elevated_durable.world_impl;

import org.jdeferred2.impl.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.comp.nextgen.inputtree.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.world.i.*;
import tripleo.elijah_durable_elevated.nextgen.inputtree.*;
import tripleo.elijah_durable_elevated.stages.gen_c.*;
import tripleo.elijah_durable_elevated.stages.inter.*;
import tripleo.elijah_elevated_durable.backbone.*;
import tripleo.elijah_elevated_durable.comp_notation.*;
import tripleo.elijah_fluffy.util.*;

public class DefaultWorldModule implements WorldModule {
	private final OS_Module                                     mod;
	private final CompilationEnclosure                          ce;
	private final ModuleThing                                   thing;
	private final Eventual<GN_PL_Run2.GenerateFunctionsRequest> erq                = new Eventual<>();
	private final DeferredObject<GenerateC, Void, Void>         _generateCDeferred = new DeferredObject<>();

	public DefaultWorldModule(final OS_Module aMod, final @NotNull CompilationEnclosure ace) {
		mod   = aMod;
		ce    = ace;
		thing = ce.addModuleThing(mod);
	}

	@Override
	public EIT_ModuleInput getEITInput() {
		return new EIT_ModuleInputImpl(mod, ce.getCompilation());
	}

	@Override
	public Eventual<GN_PL_Run2.GenerateFunctionsRequest> getErq() {
		return erq;
	}

	@Override
	public OS_Module module() {
		return mod;
	}

	@Override
	public DeferredObject<GenerateC, Void, Void> generateCDeferred() {
		return this._generateCDeferred;
	}

	public void setRq(final GN_PL_Run2.GenerateFunctionsRequest aRq) {
		erq.resolve(aRq);
	}

	public ModuleThing thing() {
		return thing;
	}

	@Override
	public String toString() {
		return "DefaultWorldModule{%s}".formatted(mod.getFileName());
	}
}
