package tripleo.elijah_durable_elevated.stages.gen_c;

import org.apache.commons.lang3.tuple.*;
import org.jdeferred2.impl.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.nextgen.reactive.*;
import tripleo.elijah_durable_elevated.*;
import tripleo.elijah_durable_elevated.stages.garish.*;
import tripleo.elijah_durable_elevated.stages.gen_fn.*;
import tripleo.elijah_durable_elevated.stages.gen_generic.*;
import tripleo.elijah_durable_elevated.stages.gen_generic.pipeline_impl.*;
import tripleo.elijah_durable_elevated.world.i.*;
import tripleo.elijah_elevated_durable.comp.*;
import tripleo.elijah_fluffy.util.*;

public class WhyNotGarish_Class implements WhyNotGarish_Item {
	private static final boolean                                       __Oct_13       = false;
	private final        EvaClass                                      gc;
	private final        GenerateC                                     generateC;
	private final        DeferredObject<GenerateResultEnv, Void, Void> fileGenPromise = new DeferredObject<>();
	@SuppressWarnings("TypeMayBeWeakened")
	private final        GCFC                                          gcfc           = new GCFC();
	public WhyNotGarish_Class(final EvaClass aGc, final GenerateC aGenerateC) {
		gc        = aGc;
		generateC = aGenerateC;

		gc.reactive().add(gcfc);

		fileGenPromise.then(this::onFileGen);
	}

	private void onFileGen(final @NotNull GenerateResultEnv aFileGen) {
		//assert false;
		NotImplementedException.raise();

		if (__Oct_13) {
			//final Compilation compilation = aFileGen.gmgm().gmr().mod().module().getContext().compilation();
			final EDL_ICompilation compilation = generateC._ce().getCompilation();

			// FIXME 10/13 which result sink?
			final GenerateResultSink resultSink2    = generateC.resultSink;
			final GenerateResultSink resultSink     = aFileGen.resultSink();
			final GenerateResult      generateResult = aFileGen.gr();
			final ElevatedLivingClass livingClass    = compilation.world().getClass(gc);
			final GarishClass         garishClass    = (GarishClass) livingClass.getGarish();

			assert resultSink == resultSink2;

			gc.generator().provide(resultSink, garishClass, generateResult, generateC);
		} else {
			if (!DebugFlags.MANUAL_DISABLED) {
				gcfc.respondTo(this.generateC);
			}

			final GenerateResult generateResult = aFileGen.gr();

			final EDL_ICompilation compilation = generateC._ce().getCompilation();
			final LivingRepo       world       = compilation.world();
			final GarishClass garishClass = (GarishClass) world.getClass(gc).getGarish();

			final @NotNull GenerateResultSink sink = aFileGen.resultSink();
			if (sink == null) {
				logProgress(9991, "sink failed");
				return;
			}

			sink.add(new GarishClass__addClass_1(garishClass, generateResult, generateC));
		}
	}

	@SuppressWarnings("SameParameterValue")
	private void logProgress(int code, String message) {
		generateC._ce().logProgress(CompProgress.GenerateC, Pair.of(code, message));
	}

	public String getTypeNameString() {
		return GenerateC.GetTypeName.forGenClass(gc);
	}

	@Override
	public boolean hasFileGen() {
		return fileGenPromise.isResolved();
	}

	@Override
	public void provideFileGen(final GenerateResultEnv fg) {
		fileGenPromise.resolve(fg);
	}

	public EvaClass evaNode() {
		return gc;
	}

	public class GCFC implements Reactivable {

		@Override
		public void respondTo(final ReactiveDimension aDimension) {
			if (aDimension instanceof GenerateC generateC2) {
				fileGenPromise.then(fileGen -> {
					final ElevatedLivingClass livingClass = generateC2._ce().getCompilation().world().getClass(gc);

					livingClass.generateWith(fileGen.resultSink(), livingClass.getGarish(), fileGen.gr(), generateC2);
				});
			}
		}
	}
}
