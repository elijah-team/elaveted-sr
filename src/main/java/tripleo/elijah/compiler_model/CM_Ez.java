package tripleo.elijah.compiler_model;

import tripleo.elijah.ci.*;
import tripleo.elijah.comp.queries.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.graph.*;

public interface CM_Ez extends CM_Base {
	void advise(EzSpec aSpec);

	void advise(Operation2<CompilerInstructions> aCio);

	void advise(CK_ObjectTree aObjectTree);

	void advise(QSEZ_Reasoning aReasoning);

	EzSpec spec();

	CK_SourceFile sourceFile();

	String hash(); // Let's not Operation2
}
