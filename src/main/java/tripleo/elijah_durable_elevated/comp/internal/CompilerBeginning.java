package tripleo.elijah_durable_elevated.comp.internal;

import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah_elevated_durable.comp.*;

import java.util.*;

public record CompilerBeginning(
		EDL_ICompilation compilation,
		CompilerInstructions rootCI,
		List<CompilerInput> compilerInput,
		IProgressSink progressSink,
		EDL_ICompilation.CompilationConfig cfg
) {


	@Override
	public String toString() {
		return "CompilerBeginning[" +
				"compilation=" + compilation + ", " +
				"rootCI=" + rootCI + ", " +
				"compilerInput=" + compilerInput + ", " +
				"progressSink=" + progressSink + ", " +
				"cfg=" + cfg + ']';
	}

}
