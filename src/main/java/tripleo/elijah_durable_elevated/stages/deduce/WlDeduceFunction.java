package tripleo.elijah_durable_elevated.stages.deduce;

import tripleo.elijah.g.*;
import tripleo.elijah_durable_elevated.stages.gen_fn.*;
import tripleo.elijah_durable_elevated.stages.inter.*;
import tripleo.elijah_durable_elevated.work.*;
import tripleo.elijah_fluffy.util.*;

import java.util.*;

class WlDeduceFunction implements WorkJob {
    private final DeduceTypes2 deduceTypes2;
    private final List<BaseEvaFunction> coll;
    private final WorkJob workJob;
    private boolean _isDone;

    public WlDeduceFunction(final DeduceTypes2 aDeduceTypes2, final WorkJob aWorkJob, List<BaseEvaFunction> aColl) {
        deduceTypes2 = aDeduceTypes2;
        workJob = aWorkJob;
        coll = aColl;
    }

    @Override
    public void run(final WorkManager aWorkManager) {
        // README coll seems out of place here

        // TODO assumes result is in the same file as this (DeduceTypes2)

        if (workJob instanceof WlGenerateFunction) {
            final EvaFunction generatedFunction1 = ((WlGenerateFunction) workJob).getResult();
            if (!coll.contains(generatedFunction1)) {
                coll.add(generatedFunction1);
                if (!generatedFunction1.deducedAlready) {
                    GCompilationEnclosure ce =
                            deduceTypes2._phase().ca().getCompilation().getCompilationEnclosure();
                    GModuleThing mt = ce.addModuleThing(generatedFunction1.module());

                    deduceTypes2.deduce_generated_function(generatedFunction1, (ModuleThing) mt);
                }
                generatedFunction1.deducedAlready = true;
            }
        } else if (workJob instanceof final WlGenerateDefaultCtor generateDefaultCtor) {
            // TODO 10/17 PromiseExpectationBuilder, annoy
            generateDefaultCtor.getGenerated().then(result -> {
                final EvaConstructor evaConstructor = (EvaConstructor) result;
                if (!coll.contains(evaConstructor)) {
                    coll.add(evaConstructor);
                    if (!evaConstructor.deducedAlready) { // TODO 10/17 queue_deduce
                        deduceTypes2.deduce_generated_constructor(evaConstructor);
                    }
                    evaConstructor.deducedAlready = true;
                }
            });
        } else if (workJob instanceof WlGenerateCtor) {
            final EvaConstructor evaConstructor = ((WlGenerateCtor) workJob).getResult();
            if (!coll.contains(evaConstructor)) {
                coll.add(evaConstructor);
                if (!evaConstructor.deducedAlready) {
                    deduceTypes2.deduce_generated_constructor(evaConstructor);
                }
                evaConstructor.deducedAlready = true;
            }
        } else throw new NotImplementedException();

        assert coll.size() == 1;

        _isDone = true;
    }

    @Override
    public boolean isDone() {
        return _isDone;
    }
}
