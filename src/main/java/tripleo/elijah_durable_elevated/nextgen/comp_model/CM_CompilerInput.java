package tripleo.elijah_durable_elevated.nextgen.comp_model;

import lombok.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.nextgen.outputtree.*;
import tripleo.elijah_elevated_durable.comp.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.wrap.*;

public class CM_CompilerInput implements EOT_Nameable {
	private final EDL_ICompilation comp;
	private final CompilerInput    carrier;
	private final String           inp;
	//@SuppressWarnings({"RedundantSuppression"})
	@Getter
	private       CompilerInput.Ty ty;
	private       File             dir_carrier;

	public CM_CompilerInput(final CompilerInput aCompilerInput, final EDL_ICompilation aCompilation) {
		carrier = aCompilerInput;
		comp = aCompilation;
		inp  = carrier.getInp();
	}

	public boolean inpSameAs(final String aS) {
		return aS.equals(this.inp);
	}

	//public void setInp(final String aInp) {
	//	inp = aInp;
	//}

	//public void setSourceRoot() {
	//	ty = CompilerInput.Ty.SOURCE_ROOT;
	//}

	public void setDirectory(final File aF) {
		ty          = CompilerInput.Ty.SOURCE_ROOT;
		dir_carrier = aF;
	}

	public File fileOf() {
		final File f = new File(inp);
		return f;
	}

	public void onIsEz() {
		final ILazyCompilerInstructions ilci = comp.con().createLazyCompilerInstructions(carrier);

		final Maybe<ILazyCompilerInstructions> m4 = new Maybe<>(ilci, null);
		carrier.accept_ci(m4);
	}

	@SuppressWarnings("SuspiciousGetterSetter")
	@Override
	public String getNameableString() {
		return inp;
	}
}
