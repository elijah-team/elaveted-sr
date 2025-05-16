package tripleo.elijah_durable_elevated.comp.nextgen.impl;

import lombok.*;
import tripleo.elijah.comp.nextgen.inputtree.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.inputtree.*;
import tripleo.elijah_elevated_durable.comp.*;
import tripleo.elijah_fluffy.util.*;

public class EIT_CK_SourceFileInput implements EIT_Input {
	@SuppressWarnings("FieldCanBeLocal")
	private final __CK_SourceFile__AbstractEzFile file;
	//@Override
	@Setter
	private       EIT_ModuleInput                 moduleInput;

	public EIT_CK_SourceFileInput(final __CK_SourceFile__AbstractEzFile aFile) {
		file = aFile;
	}

	@Override
	public EIT_InputType getType() {
		return EIT_InputType.ELIJAH_SOURCE;
	}

	@SuppressWarnings("SuspiciousGetterSetter")
	//@Override
	public EIT_ModuleInput getInput() {
		return moduleInput;
	}//EIT_Mod

	public Eventual<OS_Module> parsed(EDL_ICompilation aCompilation) {
		ElijahFileParser p = new ElijahFileParser();
		p.configure(aCompilation);
		p.configure(aCompilation.use_elijahCache()); // :smiley:
		return p.getEventual();
	}
}
