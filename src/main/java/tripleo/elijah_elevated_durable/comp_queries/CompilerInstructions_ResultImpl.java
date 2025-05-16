package tripleo.elijah_elevated_durable.comp_queries;

import org.apache.commons.lang3.tuple.*;
import org.jdeferred2.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.queries.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.compiler_model.*;
import tripleo.elijah_elevated_durable.comp.*;
import tripleo.elijah_elevated_durable.compiler_model.*;
import tripleo.elijah_fluffy.diagnostic.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.wrap.*;

import java.util.*;
import java.util.stream.*;

public class CompilerInstructions_ResultImpl implements CompilerInstructions_Result {
	private final List<Pair<Operation2<CompilerInstructions>, QSEZ_Reasoning>> directoryResult  = new ArrayList<>();
	private final List<CM_Ez>                                                  directoryResult2 = new ArrayList<>();
	private       File                                                         directory;
	//private       QuerySearchEzFiles                                           q;
	private       Eventual<Compilation>                                        compilationP     = new Eventual<>();

	@Override
	public List<Operation2<CompilerInstructions>> getDirectoryResult() {
		return directoryResult.stream()
				.map(it -> it.getKey())
				.collect(Collectors.toList());
	}

	@Override
	public List<Pair<Operation2<CompilerInstructions>, QSEZ_Reasoning>> getDirectoryResult2() {
		return directoryResult;
	}

	@Override
	public void add(final Operation2<CompilerInstructions> aCio, final QSEZ_Reasoning aReasoning) {
		directoryResult.add(Pair.of(aCio, aReasoning));
		{
			compilationP.then(new DoneCallback<Compilation>() {
				@Override
				public void onDone(final Compilation result) {
					final EDL_ICompilation Scomp = (EDL_ICompilation) result;
					Scomp.megaGrande((EzSpec) null);
					final CM_Ez cm = new EDL_CM_Ez();
					//cm.advise((EzSpec)null);
					cm.advise(aCio);
					//cm.advise(((Compilation)null).getObjectTree());
					directoryResult2.add(cm);
				}
			});
		}
	}

	@Override
	public void add(final CompilerInstructions aCio, final @Nullable Diagnostic failure, final QSEZ_Reasoning aReasoning) {
		assert failure == null;
		add(Operation2.success(aCio), aReasoning);
	}

	@Override
	public void setDirectory(final File aDirectory) {
		this.directory = aDirectory;
	}

	@Override
	public void advise(final CompilationClosure aCompilationClosure) {
		compilationP.resolve(aCompilationClosure.getCompilation());
	}
}
