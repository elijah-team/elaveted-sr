package tripleo.elijah_durable_elevated.comp.nextgen.impl;

import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.inputtree.*;
import tripleo.elijah.nextgen.outputtree.*;
import tripleo.elijah_durable_elevated.*;
import tripleo.elijah_durable_elevated.comp.nextgen.*;
import tripleo.elijah_elevated_durable.comp.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.graph.*;
import tripleo.wrap.*;

import java.util.*;
import java.util.regex.*;

abstract class __CK_SourceFile__AbstractElijahFile implements CK_SourceFile {
	protected CK_GlobalRef  compilation;
	protected CompilerInput input;

	public static boolean isElijjahFile(String aFileName) {
		final boolean todoRemoveMe = Pattern.matches(".+\\.elijjah$", aFileName);
		if (todoRemoveMe) return true;
		final boolean matches2regularExtensionMatches = Pattern.matches(".+\\.elijah$", aFileName);
		//noinspection RedundantIfStatement
		if (matches2regularExtensionMatches) return true;
		return false;
	}

	public static Eventual<OS_Module> realParseElijahFile2(final ElijahSpec spec, final ElijahCache cache, final EDL_ICompilation compilation) {
		ElijahFileParser p = new ElijahFileParser();
		p.configure(cache);
		p.configure(compilation);
		final Eventual<OS_Module> x = p.parse(spec);
		assert !x.isPending();
		final OS_Module extracted = EventualExtract.of(x);
		if (extracted == null) {
			throw new AssertionError();
		}
		return p.getEventual();
	}

	public Operation2<OS_Module> realParseElijahFile(final ElijahSpec spec, final ElijahCache cache) {
		return realParseElijahFile(spec, cache, compilation.asCompilation());
	}

	/**
	 * - I don't remember what absolutePath is for - Cache doesn't add to QueryDB
	 * <p>
	 * STEPS ------
	 * <p>
	 * 1. Get absolutePath<br/>
	 * 2. Check cache, return early<br/>
	 * 3. Parse (Query is incorrect I think)<br/>
	 * 4. Cache new result<br/>
	 * </p>
	 *
	 * @param spec
	 * @param cache
	 * @return
	 */
	public static Operation2<OS_Module> realParseElijahFile(final ElijahSpec spec, final ElijahCache cache, final EDL_ICompilation compilation) {
		if (DebugFlags.MakeSense) {
			ElijahFileParser p = new ElijahFileParser();
			p.configure(cache);
			p.configure(compilation);
			final Eventual<OS_Module> x = p.parse(spec);
			assert !x.isPending();
			final OS_Module extracted = EventualExtract.of(x);
			if (extracted == null) {
				throw new AssertionError();
			}
			return Operation2.success(extracted);
		} else {
			final String absolutePath = spec.getLongPath2();

			final Optional<OS_Module> early = cache.get(absolutePath);

			if (early.isPresent()) {
				return Operation2.success(early.get());
			}

			final Operation2<OS_Module> om = CX_ParseElijahFile.parseAndCache(spec, cache, absolutePath, compilation);
			return om;
		}
	}

	@Override
	public CompilerInput compilerInput() {
		return input;
	}

	@Override
	public EIT_Input input() {
		// this.input
		return new EIT_Input() {
			@Override
			public EIT_InputType getType() {
				return EIT_InputType.EZ_FILE;
			}
		};
	}

	@Override
	public EOT_OutputFile output() {
		throw new UnintendedUseException("TODO 12/?? implement me");
	}

	public abstract Eventual<OS_Module> process_queryElijah();

	@Override
	public Eventual<OS_Module> process_queryElijah2() {
		return process_queryElijah();
	}

	//protected void asserverate() {
	//}

	@Override
	public void advise(final CompilationClosure aCc) {
		compilation = (CK_GlobalRef) aCc.getCompilation();
	}

	@Override
	public void advise(final CompilerInput aInput, final CompilationClosure aCc) {
		input       = aInput;
		compilation = (CK_GlobalRef) aCc.getCompilation();
	}

	protected abstract File getFile();

	public abstract String getFileName();
}
