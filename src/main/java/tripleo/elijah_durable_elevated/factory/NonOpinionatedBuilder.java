package tripleo.elijah_durable_elevated.factory;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah_durable_elevated.comp.*;
import tripleo.elijah_durable_elevated.work.*;
import tripleo.elijah_elevated_durable.comp.*;
import tripleo.elijah_elevated_durable.comp.input.*;

import java.util.*;
import java.util.stream.*;

public class NonOpinionatedBuilder {
	public NonOpinionatedBuilder() {}

	public List<CompilerInput> inputs(final List<String> args) {
		final List<CompilerInput> inputs = args.stream()
				.map(s -> {
					final CompilerInput input = createCompilerInput_simple(s);
					if (!s.startsWith("-")) {//cm.inpSameAs(s)) {
						input.setSourceRoot();
					}
					return input;
				})
				.collect(Collectors.toList());
		return inputs;
	}

	@NotNull
	private static CompilerInput createCompilerInput_simple(final String s) {
		final CompilerInput    input = new EDL_CompilerInput(s, null);
		return input;
	}

	public EDL_CompilerController createCompilerController(final ICompilationAccess3 aCompilationAccess3) {
		return new EDL_CompilerController(aCompilationAccess3);
	}

	public WorkList createWorkList(final Object contextAkaOpinion) {
		return new EDP_WorkList();
	}
}
