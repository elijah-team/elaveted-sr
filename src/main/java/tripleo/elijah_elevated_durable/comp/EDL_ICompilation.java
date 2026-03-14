package tripleo.elijah_elevated_durable.comp;

import clojure.lang.*;
import io.reactivex.rxjava3.core.Observer;
import org.jdeferred2.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.nextgen.pn.*;
import tripleo.elijah.comp.nextgen.pw.*;
import tripleo.elijah.comp.percy.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.compiler_model.*;
import tripleo.elijah.g.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.inputtree.*;
import tripleo.elijah.nextgen.outputtree.*;
import tripleo.elijah_durable_elevated.comp.*;
import tripleo.elijah_durable_elevated.comp.i.*;
import tripleo.elijah_durable_elevated.comp.i.extra.*;
import tripleo.elijah_durable_elevated.comp.internal.*;
import tripleo.elijah_durable_elevated.nextgen.comp_model.*;
import tripleo.elijah_durable_elevated.stages.deduce.fluffy.i.*;
import tripleo.elijah_durable_elevated.world.i.*;
import tripleo.elijah_elevated_durable.backbone.*;
import tripleo.elijah_elevated_durable.lang_model.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.graph.*;
import tripleo.paths.*;

import java.util.*;

// TODO 01/19 might be fluffy
public interface EDL_ICompilation extends Compilation {
	CompilationInterfaceRevised2 revised2();

	<T> void addInput(final EOT_Nameable aNameable, final EIT_InputType ty, final Class<T> aClass, final java.util.function.Supplier<T> aSupplier);

	CM_Module megaGrande(OS_Module aModule);

	void set_pa(IPipelineAccess a_pa);

	void addCompilerInputWatcher(CN_CompilerInputWatcher aCNCompilerInputWatcher);

	void compilerInputWatcher_Event(CN_CompilerInputWatcher.e aEvent, CompilerInput aCompilerInput, Object aO);

	EDL_CIS _cis();

	CompFactory con();

	void setIO(IO io);

	@NotNull
	FluffyComp getFluffy();

	Operation<Ok> hasInstructions(@NotNull List<CompilerInstructions> cis, @NotNull IPipelineAccess pa);

	ModuleBuilder moduleBuilder();

	void subscribeCI(@NotNull Observer<CompilerInstructions> aCio);

	@Override
	List<CompilerInput> getInputs();

	@Override
	void pushItem(CompilerInstructions aci);

	@Override
	void use(@NotNull CompilerInstructions compilerInstructions, USE_Reasoning aReasoning);

	@Override
	ElijahCache use_elijahCache();

	@Override
	void pushWork(PW_PushWork aInstance, PN_Ping aPing);

	@Override
	Finally reports();

	@Override
	CompilationConfig cfg();

	@Override
	CompilationEnclosure getCompilationEnclosure();

	@Override
	Operation2<GWorldModule> findPrelude(String prelude_name);

	@Override
	IPipelineAccess pa();

	@Override
	CP_Paths paths();

	LivingRepo world();

	CM_Module megaGrande(ElijahSpec aSpec, Operation2<OS_Module> aModuleOperation);

	CM_Ez megaGrande(EzSpec aEzSpec);

	LCM_CompilerAccess getLCMAccess();

	//ICompilationRunner getRunner();

	EDL_CompilationRunner getRunner();

	CompilationConfig _cfg();

	CM_CompilerInput get(CompilerInput aInput);

	void ____m();

	//PW_CompilerController get_pw();

	CPX_Signals signals();

	Operation<Ok> maybeCheckFinishEventuals();

	LCM lcm();

	LangModel langModel();

	default void feedInputsCon2(List<String> aStringList) {
		var x= feedInputsCon(aStringList);
		NotImplementedException.raise_stop();
	}

	CompilerController feedInputsCon(List<String> aStringList);

	void onConfig(DoneCallback<IPersistentMap>
				  cb);

	void _doOnCompilation(EDL_Compilation aEdlCompilation);
}
