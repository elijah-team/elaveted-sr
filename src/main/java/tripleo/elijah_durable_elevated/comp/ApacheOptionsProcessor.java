package tripleo.elijah_durable_elevated.comp;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah_durable_elevated.comp.impl.*;
import tripleo.elijah_elevated_durable.comp.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.vendor.org_apache_commons_cli.*;

import java.util.*;

public class ApacheOptionsProcessor implements OptionsProcessor {
	private final CommandLineParser clp     = new DefaultParser();
	private final Options           options = new Options();

	@Contract(pure = true)
	public ApacheOptionsProcessor() {
		options.addOption("s", true, "stage: E: parse; O: output");
		options.addOption("silent", false, "suppress DeduceType output to console");
	}

	public Operation<Ok> process(final @NotNull EDL_ICompilation c,
								 final @NotNull List<CompilerInput> aInputs,
								 final ICompilationBus aCb) {
		try {
			final CommandLine cmd = clp.parse(options, aInputs);

			/**
			 * {@link ICompilationBus#option(CompilationChange)}
			 */

			// TODO 09/08 promises??
			// c.getCompilationEnclosure().getCompilationBus().option();

			if (EDL_Compilation.isGitlab_ci() || cmd.hasOption("silent")) {
				aCb.addCompilerChange(CC_SetSilent.class);
//				new CC_SetSilent(true).apply(c);
			}

			return Operation.success(Ok.instance());
		} catch (ParseException aE) {
			return Operation.failure(/* new DiagnosticException */(aE));
		}
	}

	@Override
	public Operation<Ok> process(final Compilation aC, final List<CompilerInput> aInputs, final ICompilationBus aCb) {
		return process((EDL_ICompilation) aC, aInputs, aCb);
	}
}
