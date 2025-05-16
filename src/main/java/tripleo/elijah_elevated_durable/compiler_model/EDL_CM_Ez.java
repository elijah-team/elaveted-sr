package tripleo.elijah_elevated_durable.compiler_model;

import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.queries.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.compiler_model.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.graph.*;

public class EDL_CM_Ez implements CM_Ez {
	private EzSpec                           spec;
	private Operation2<CompilerInstructions> cio;
	@SuppressWarnings("FieldCanBeLocal")
	private QSEZ_Reasoning                   reasoning;
	private String                           _hash;
	private CK_SourceFile _sourceFile;

	public void setHash(final String a_hash) {
		_hash = a_hash;
	}

	public void setSourceFile(final CK_SourceFile a_sourceFile) {
		_sourceFile = a_sourceFile;
	}

	@Override
	public void advise(final EzSpec aSpec) {
		this.spec = aSpec;
	}

	@Override
	public void advise(final Operation2<CompilerInstructions> aCio) {
		this.cio = aCio;
	}

	@Override
	public void advise(final @NotNull CK_ObjectTree aObjectTree) {
		aObjectTree.asseverate(this, Asseverate.CI_SPECCED);
		aObjectTree.asseverate(this, Asseverate.CI_PARSED);
	}

	@Override
	public void advise(final QSEZ_Reasoning aReasoning) {
		this.reasoning = aReasoning;
	}

	@Override
	public EzSpec spec() {
		return this.spec;
	}

	@Override
	public CK_SourceFile sourceFile() {
		return this._sourceFile;
	}

	@Override
	public String hash() {
		return this._hash;
	}
}
