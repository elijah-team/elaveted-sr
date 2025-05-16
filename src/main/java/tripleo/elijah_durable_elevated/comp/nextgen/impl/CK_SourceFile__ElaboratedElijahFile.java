package tripleo.elijah_durable_elevated.comp.nextgen.impl;

import com.google.common.base.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_elevated_durable.comp.specs.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.wrap.File;

import java.io.*;

public class CK_SourceFile__ElaboratedElijahFile extends __CK_SourceFile__AbstractElijahFile {
	protected final File   directory;
	protected final String file_name;
	protected final File   file;

	public CK_SourceFile__ElaboratedElijahFile(final File aDirectory, final String aFileName) {
		directory = aDirectory;
		file_name = aFileName;
		file      = new File(directory, file_name);
	}

	@Override
	public Eventual<OS_Module> process_queryElijah() {
		assert false;
		final ElijahCache           ezCache = null; // eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
		//compilation.getCompilationEnclosure().getCompilationRunner().ezCache();
		final Eventual<OS_Module> om = process_query(compilation.getIO(), ezCache);

		// FIXME hash
		//super.asserverate(); // FIXME 12/03 what is this? eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee

		return om;
	}

	private Eventual<OS_Module> process_query(final IO io, final @NotNull ElijahCache elijahCache) {
		final String fileName = file_name();
		Preconditions.checkArgument(isElijjahFile(fileName));

		final InputStream stream = new Supplier<InputStream>() {
			@Override
			public InputStream get() {
				try {
					return io.readFile(file);
				} catch (FileNotFoundException aE) {
					throw new RuntimeException(aE);
				}
			}
		}.get();

		final ElijahSpec elijahSpec = new EDL_ElijahSpec(fileName, file, stream);

		ElijahFileParser p = new ElijahFileParser();
		p.configure(elijahCache);
		p.configure(compilation.asCompilation());
		return p.parse(elijahSpec);
	}

	private String file_name() {
		return this.file.getName();
	}

	@Override
	protected File getFile() {
		return file;
	}

	@Override
	public String getFileName() {
		return file_name();
	}

	@Override
	public Eventual<OS_Module> process_queryElijah2() {
		if (compilation == null) assert false;
		final String fileName = file_name();
		Preconditions.checkArgument(isElijjahFile(fileName));

		final InputStream stream = new Supplier<InputStream>() {
			@Override
			public InputStream get() {
				try {
					var io = compilation.getIO();
					return io.readFile(file);
				} catch (FileNotFoundException aE) {
					throw new RuntimeException(aE);
				}
			}
		}.get();

		final ElijahSpec elijahSpec = new EDL_ElijahSpec(fileName, file, stream);

		final ElijahCache elijahCache = compilation.asCompilation().use_elijahCache();

		final ElijahFileParser p = new ElijahFileParser();
		p.configure(elijahCache);
		p.configure(compilation.asCompilation());
		//noinspection UnnecessaryLocalVariable
		final Eventual<OS_Module> ev = p.parse(elijahSpec, compilation.asCompilation());
		return ev;
	}

	@Override
	public Operation2<CompilerInstructions> process_queryEz() {
		return null;
	}

	@Override
	public Eventual<CompilerInstructions> process_queryEz2() {
		return null;
	}
}
