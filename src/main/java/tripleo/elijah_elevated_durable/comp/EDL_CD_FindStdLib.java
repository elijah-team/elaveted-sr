package tripleo.elijah_elevated_durable.comp;

import com.google.common.base.*;
import org.apache.commons.lang3.tuple.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.g.*;
import tripleo.elijah_durable_elevated.comp.internal.*;
import tripleo.elijah_durable_elevated.comp.nextgen.impl.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.graph.*;
import tripleo.paths.*;
import tripleo.wrap.*;

import java.util.function.*;

public class EDL_CD_FindStdLib implements CD_FindStdLib {
	private Operation2<CompilerInstructions> foundResult;
	private Eventual<CompilerInstructions> foundResultEv = new Eventual<>();

	@Override
	public void findStdLib(final GCR_State crState, final String aPreludeName, final Consumer<Operation2<CompilerInstructions>> coci) {
		var x = __internal_findStdLib((CR_State) crState, aPreludeName, coci);
		if (x == null) {
			throw new AssertionError();
		}
		x.then(foundResultEv::resolve);
		x.onFail(foundResultEv::reject);
	}

	private Eventual<CompilerInstructions> __internal_findStdLib(final @NotNull CR_State crState,
																 final @NotNull String aPreludeName,
																 final @NotNull Consumer<Operation2<CompilerInstructions>> coci) {
		final ICompilationRunner    compilationRunner = crState.runner();
		final /*EDL_I*/ Compilation cc                = (EDL_Compilation) compilationRunner._accessCompilation();
		final CP_StdlibPath         slr               = cc.paths().stdlibRoot();
		final CP_Path               pl                = slr.child("lib-" + aPreludeName);
		final CP_Path               sle               = pl.child("stdlib.ez");

		@NotNull Operation2<CompilerInstructions> result = null;

		final CK_SourceFile                  sourceFile2 = CK_SourceFileFactory.get(sle, CK_SourceFileFactory.K.SpecifiedPathEzFile);
		sourceFile2.advise(cc.getCompilationClosure());
		final Eventual<CompilerInstructions> cie         = sourceFile2.process_queryEz2();

		try {
			final File local_stdlib = sle.toFile();

			cc.getCompilationEnclosure().logProgress(CompProgress.DriverPhase, Pair.of(3939, "" + local_stdlib));

			if (local_stdlib.exists()) {
				try {
					sourceFile2.advise(cc.getCompilationClosure());

					cie.then((CompilerInstructions Sci) -> {

						//if (false) { // matrix test
						//	//noinspection StatementWithEmptyBody
						//	if (result.mode() == Mode.SUCCESS) {
						//		cc.getCompilation().pushItem(result.success());
						//	} else {
						//		// README otherwise pass through
						//	}
						//}

						Preconditions.checkNotNull(Sci);

						final Operation2<CompilerInstructions> result1 = Operation2.success(Sci);
						foundResult = result1;
						coci.accept(result1);
					});

					return cie;
				} catch (final Exception e) {
					foundResult = result;
					coci.accept(result);

					assert cie != null;
					cie.reject(e); // honestly, expecting a problem here
					return cie;
				}
			}
			throw new NeverReachedException();
		} catch (Exception e) {
			cie.reject(e); // honestly, expecting a problem here
			return cie;
		}
	}

	@Override
	public CompilerInstructions maybeFoundResult() {
		if (foundResult.mode() == Mode.SUCCESS)
			return foundResult.success();
		return null;
	}

	public final @NotNull Eventual<CompilerInstructions> findStdLib2(final GCR_State crState, final String aPreludeName) {
		Eventual<Eventual<CompilerInstructions>> resident = new Eventual<>();
		// TODO Does not look like it makes sense...
		final Eventual<CompilerInstructions> res = __internal_findStdLib((CR_State) crState, aPreludeName, Soci -> findStdLib(crState, aPreludeName, oci -> resident.then(Sres-> {
			if (oci.mode() != Mode.SUCCESS) {
				throw new AssertionError();
			}
			if (Sres.isPending()) {
				System.err.println("240930-0107 **NO** double set");
				Sres.resolve(oci.success());
			} else {
				System.err.println("240930-0109 Double set");
			}
		})));
		resident.resolve(res);
		return res;
	}

	public Eventual<CompilerInstructions> getFoundResultEv() {
		return foundResultEv;
	}
}
