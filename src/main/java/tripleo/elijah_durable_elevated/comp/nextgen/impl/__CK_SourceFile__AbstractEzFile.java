package tripleo.elijah_durable_elevated.comp.nextgen.impl;

import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.inputtree.*;
import tripleo.elijah.nextgen.outputtree.*;
import tripleo.elijah_elevated_durable.backbone.*;
import tripleo.elijah_elevated_durable.comp.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.graph.*;
import tripleo.paths.*;
import tripleo.wrap.File;

import java.io.*;
import java.util.regex.*;

abstract class __CK_SourceFile__AbstractEzFile implements CK_SourceFile {
	protected EDL_ICompilation compilation;
	protected CompilerInput    input;

	public static boolean isEzFile(String aFileName) {
		return Pattern.matches(".+\\.ez$", aFileName);
	}

	@Override
	public CompilerInput compilerInput() {
		return input;
	}

	@Override
	public EIT_Input input() {
		return new EIT_CK_SourceFileInput(this);
	}

	@Override
	public EOT_OutputFile output() {
		throw new UnintendedUseException();
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
	public void advise(final CompilationClosure aCc) {
		compilation = (EDL_ICompilation) aCc.getCompilation();
	}

	@Override
	public void advise(final CompilerInput aInput, final CompilationClosure aCc) {
		input       = aInput;
		compilation = (EDL_ICompilation) aCc.getCompilation();
	}

	protected void asseverate() {
		if (getFileName() == null) return;
		if (compilation == null) return;

		final String            file_name = getFileName();
		final tripleo.wrap.File file = getFile();
		final Operation<String> hash = CA_getHashForFile.apply(file_name, file);

        if (hash.mode() == Mode.SUCCESS) {
            System.err.println("240921-0100 " + hash.success());
        } else {
            System.err.println("959595");
		}

		compilation.getObjectTree().asseverate(new Asseveration() {
			@Override
			public Object target() {
				return __CK_SourceFile__AbstractEzFile.this;
			}

			@Override
			public Asseverate code() {
				return Asseverate.CI_HASHED;
			}

			@Override
			public void onLogProgress(final Asseverable asseverable_ce) {
				// FIXME Over-engineered: Replace w/ c.revised().addProgressMarker()
				if (asseverable_ce instanceof CompilationEnclosure) {
					final CompilationEnclosure ce = (CompilationEnclosure) asseverable_ce;
					//ce.getCompilation().revised(). // fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff
					//noinspection Convert2Lambda
					ce.logProgress2(CompProgress.Ez__HasHash, new AsseverationLogProgress() {
						@Override
						public void call(PrintStream out, PrintStream err) {
							out.printf("[-- Ez has HASH ] %s %s%n", file_name, hash.success());
						}
					});
				} else {
					throw new AssertionError();
				}
			}
		});
	}

	public abstract String getFileName();

	protected abstract File getFile();
}
