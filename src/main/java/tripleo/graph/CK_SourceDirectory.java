package tripleo.graph;

import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.queries.*;
import tripleo.elijah_durable_elevated.comp.nextgen.impl.*;
import tripleo.elijah_elevated_durable.comp_queries.*;
import tripleo.wrap.File;

import java.io.*;
import java.util.*;

public class CK_SourceDirectory {
	private final File    directory;
	private final CompilationClosure cc;
	private final List<CI2>          contents = new ArrayList<>();

	public CK_SourceDirectory(final File aDirectory, final @NotNull CompilationClosure aCc) {
		directory = aDirectory;
		cc = aCc;
	}

	public List<CI2> elaborateGet(final @NotNull File directory, final FilenameFilter ez_files_filter) {
		List<CI2> RR = new ArrayList<>();
		for (final String file_name : list(ez_files_filter)) {
			final CK_SourceFile sf = CK_SourceFileFactory.get(directory, file_name, CK_SourceFileFactory.K.ElaboratedEzFile);
			sf.advise(cc);
			// reason obv is it is elaborated in the directory ...
			sf.process_queryEz2().then(Sci -> {
				RR.add(new CI2(Sci, QSEZ_Reasonings.create(QSEZ_Reasonings.Ty.EID)));
				add(Sci, QSEZ_Reasonings.create(QSEZ_Reasonings.Ty.EID));
			});
		}
		return RR;
	}

	public Iterable<String> list(final FilenameFilter aFilter) {
		final String[]     list  = directory.list(aFilter);
		final List<String> list1 = new ArrayList<>();
		if (list != null) {
			list1.addAll(Arrays.asList(list));
		}
		return list1;
	}

	public void add(final CompilerInstructions aSci, final QSEZ_Reasoning aReasoning) {
		var x = aSci.getFilename();
		contents.add(new CI2(aSci, aReasoning));
	}

	public record CI2(CompilerInstructions ci, QSEZ_Reasoning reasoning) {
	}
}
