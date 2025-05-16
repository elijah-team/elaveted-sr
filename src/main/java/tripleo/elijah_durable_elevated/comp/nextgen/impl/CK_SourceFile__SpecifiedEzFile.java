package tripleo.elijah_durable_elevated.comp.nextgen.impl;

import com.google.common.base.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.wrap.File;

import java.io.*;

public class CK_SourceFile__SpecifiedEzFile extends __CK_SourceFile__AbstractEzFile {
	private final File file;

	public CK_SourceFile__SpecifiedEzFile(final File aFile) {
		file = aFile;
	}

	@Override
	public Operation2<CompilerInstructions> process_queryEz() {
		final IO               io       = compilation.getIO();
		final @NotNull EzCache ezCache  = compilation.getCompilationEnclosure().getCompilationRunner().ezCache();
		final String fileName = file_name();
		Preconditions.checkArgument(isEzFile(fileName));

		if (true) {
			return _process_queryEz2(ezCache, fileName, io).oci();
		} else {
			//var ezSpec = new EDL_EzSpec(
			//		fileName,
			//		file, () -> {
			//	try {
			//		return Operation.success(file.readFile(io));
			//	} catch (FileNotFoundException aE) {
			//		return Operation.failure(aE);
			//	}
			//}
			//);
			//final Operation2<CompilerInstructions> oci = realParseEzFile(ezSpec, ezCache);
			//super.asserverate();
			//return oci;
			return null;
		}
	}

	@Override
	public Eventual<CompilerInstructions> process_queryEz2() {
		Preconditions.checkNotNull(compilation);

		final String fileName = file_name();
		Preconditions.checkNotNull(fileName);
		Preconditions.checkArgument(isEzFile(fileName));

		final IO               io      = compilation.getIO();
		final @NotNull EzCache ezCache = compilation.getCompilationEnclosure().getCompilationRunner().ezCache();

		var p = _process_queryEz2(ezCache, fileName, io);
		return p.getEventual();
	}

	public EzFileParser _process_queryEz2(final @NotNull EzCache ezCache, final String fileName, final IO io) {
		final EzFileParser p = new EzFileParser();
		p.configure(ezCache);
		@NotNull final Operation<InputStream> inputStream = getInputStreamOperation(io);
		switch (inputStream.mode()) {
		case SUCCESS -> p.parse(fileName, this.file, inputStream);
		case FAILURE -> this.compilation.getErrSink().exception(inputStream.failure());
		}
		super.asseverate();
		return p;
	}

	@Override
	public Eventual<OS_Module> process_queryElijah() {
		return null;
	}

	@Override
	public Eventual<OS_Module> process_queryElijah2() {
		return null;
	}

	private String file_name() {
		return this.file.getName();
	}

	@NotNull
	private Operation<InputStream> getInputStreamOperation(final IO io) {
		try {
			return Operation.success(file.readFile(io));
		} catch (FileNotFoundException aE) {
			return Operation.failure(aE);
		}
	}

	@Override
	public String getFileName() {
		return file_name();
	}

	@Override
	protected File getFile() {
		return file;
	}
}
