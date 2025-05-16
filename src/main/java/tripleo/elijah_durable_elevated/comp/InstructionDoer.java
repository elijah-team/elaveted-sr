package tripleo.elijah_durable_elevated.comp;

import tripleo.elijah.ci.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah_elevated_durable.comp.*;
import tripleo.elijah_fluffy.diagnostic.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.elijah_potential.comp.*;

public class InstructionDoer implements CompletableProcess<CompilerInstructions> {
	private final EDL_ICompilation     compilation1;
	public        CompilerInstructions root;

	public InstructionDoer(EDL_ICompilation compilation1) {
		this.compilation1 = compilation1;
	}

	@Override
	public void add(final CompilerInstructions item) {
		ICompilationRunner __cr = compilation1.getCompilationEnclosure().getCompilationRunner();
		if (root == null) {
			root = item;
			try {
				compilation1.setRootCI(root);

//				__cr.start(compilation1.getRootCI(), compilation1.pa());
			} catch (Exception aE) {
				throw new RuntimeException(aE);
			}
		} else {
			tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon.println_err_4("second: " + item.getFilename());

			var compilation = __cr.c();

			compilation.use(item, USE_Reasonings.instruction_doer_addon(item));
		}
	}

	@Override
	public void complete() {
		tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon.println_err_4("InstructionDoer::complete");
	}

	@Override
	public void error(final Diagnostic d) {
		tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon.println_err_4("InstructionDoer::error");
	}

	@Override
	public void preComplete() {
		tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon.println_err_4("InstructionDoer::preComplete");
	}

	@Override
	public void start() {
		tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon.println_err_4("InstructionDoer::start");
	}
}
