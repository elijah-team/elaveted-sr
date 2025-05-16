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

public class CK_SourceFile__ElaboratedEzFile extends __CK_SourceFile__AbstractEzFile {
	protected final File   directory;
	protected final String file_name;
	protected final File   file;

	public CK_SourceFile__ElaboratedEzFile(final File aDirectory, final String aFileName) {
		directory = aDirectory;
		file_name = aFileName;
		file      = new File(directory, file_name);
	}

	@Override
	public Operation2<CompilerInstructions> process_queryEz() {
		final EzCache      ezCache = compilation.getCompilationEnclosure().getCompilationRunner().ezCache();
		final EzFileParser p       = process_query(compilation.getIO(), ezCache);

		super.asseverate();

		return p.oci();
	}

	@Override
	public Eventual<CompilerInstructions> process_queryEz2() {
		final EzCache      ezCache = compilation.getCompilationEnclosure().getCompilationRunner().ezCache();
		final EzFileParser p       = process_query(compilation.getIO(), ezCache);

		var lcm = compilation.lcm();

		super.asseverate();

		return p.getEventual();
	}

	private EzFileParser process_query(final IO io, final @NotNull EzCache ezCache) {
		EzFileParser p = new EzFileParser();
		p.configure(ezCache);
		final String fileName = file_name();
		Preconditions.checkArgument(isEzFile(fileName));

		if (true) {
			@NotNull final Operation<InputStream> inputStream = getInputStreamOperation(io);
			switch (inputStream.mode()) {
			case SUCCESS -> p.parse(fileName, this.file, inputStream);
			case FAILURE -> this.compilation.getErrSink().exception(inputStream.failure());
			}
		} else {
			//var specOp1 = EDL_EzSpec.of(
			//		fileName,
			//		file,
			//		//file.getFileInputStreamOperationSupplier() // eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
			//		new Supplier<Operation<InputStream>>() {
			//			@Override
			//			public Operation<InputStream> get() {
			//				return getInputStreamOperation();
			//			}
			//		}.get()
			//						);
		}
		return p;
	}

	private String file_name() {
		//return this.file.wrappedFileName(); // eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
		return this.file.wrapped().toString(); // README 12/07 not just toString, but wrapped.toString
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
	public Eventual<OS_Module> process_queryElijah() {
		return null;
	}

	@Override
	public Eventual<OS_Module> process_queryElijah2() {
		return null;
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
