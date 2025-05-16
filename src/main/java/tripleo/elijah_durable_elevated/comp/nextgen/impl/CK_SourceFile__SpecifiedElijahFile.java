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

public class CK_SourceFile__SpecifiedElijahFile extends __CK_SourceFile__AbstractElijahFile {
	private final File file;

	public CK_SourceFile__SpecifiedElijahFile(final File aFile) {
		file = aFile;
	}

	@Override
	public Operation2<CompilerInstructions> process_queryEz() {
		return null;
	}

	@Override
	public Eventual<CompilerInstructions> process_queryEz2() {
		final ElijahCache           elijahCache = compilation.asCompilation().use_elijahCache();
		final Operation2<OS_Module> oci         = process_query(compilation.getIO(), elijahCache);

		//super.asserverate();

//		return oci;
		return null;
	}

	@Override
	public Eventual<OS_Module> process_queryElijah() {
		return null;
	}

	@Override
	public Eventual<OS_Module> process_queryElijah2() {
		return null;
	}

	private Operation2<OS_Module> process_query(final IO io, final @NotNull ElijahCache aElijahCache) {
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

		ElijahSpec elijahSpec = new EDL_ElijahSpec(fileName, file, stream);
		return realParseElijahFile(elijahSpec, aElijahCache);
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
}
