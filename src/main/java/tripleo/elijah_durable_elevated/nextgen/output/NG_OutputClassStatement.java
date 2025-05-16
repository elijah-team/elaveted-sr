package tripleo.elijah_durable_elevated.nextgen.output;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.nextgen.inputtree.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.outputstatement.*;
import tripleo.elijah_durable_elevated.nextgen.inputtree.*;
import tripleo.elijah_durable_elevated.stages.gen_generic.GenerateResult.*;
import tripleo.elijah_elevated_durable.comp.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.util.buffer.*;

public class NG_OutputClassStatement implements NG_OutputStatement {
	private final          Buffer    buf;
	private final          TY        ty;
	private final @NotNull NG_OutDep moduleDependency;

	public NG_OutputClassStatement(final @NotNull BufferTabbedOutputStream aBufferTabbedOutputStream,
	                               final @NotNull OS_Module aModuleDependency,
	                               final TY aTy) {
		buf              = aBufferTabbedOutputStream.getBuffer();
		ty               = aTy;
		moduleDependency = new NG_OutDep(aModuleDependency);
	}

	@Override
	public @NotNull EX_Explanation getExplanation() {
		return EX_Explanation.withMessage("NG_OutputClassStatement");
	}

	@Override
	@NotNull
	public EIT_ModuleInput getModuleInput() {
		var m = moduleDependency().module();

		final EIT_ModuleInput moduleInput = new EIT_ModuleInputImpl(m, (EDL_ICompilation) m.getCompilation());
		return moduleInput;
	}

	@Override
	public String getText() {
		return buf.getText();
	}

	@Override
	public @NotNull TY getTy() {
		return ty;
	}

	public NG_OutDep moduleDependency() {
		return moduleDependency;
	}
}
