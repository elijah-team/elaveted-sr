package tripleo.elijah_durable_elevated.pre_world;


import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.lang.i.FunctionDef;
import tripleo.elijah_durable_elevated.entrypoints.MainClassEntryPoint;
import tripleo.elijah_durable_elevated.stages.deduce.ClassInvocation;
import tripleo.elijah_durable_elevated.stages.deduce.FunctionInvocation;
import tripleo.elijah_durable_elevated.stages.gen_fn.GenerateFunctions;
import tripleo.elijah_durable_elevated.stages.gen_fn.IClassGenerator;
import tripleo.elijah_durable_elevated.stages.inter.ModuleThing;
import tripleo.small.ES_Symbol;

import java.util.Objects;

import tripleo.elijah_fluffy.util.Helpers.*;

import static tripleo.elijah_durable_elevated.util.Helpers0.List_of;

public class Mirror_MainClassEntryPoint implements Mirror_EntryPoint {
	private final MainClassEntryPoint mcep;
	private final ModuleThing         mt;
	private final GenerateFunctions   gf;

	public Mirror_MainClassEntryPoint(final MainClassEntryPoint aMcep,
									  final @NotNull ModuleThing aMt,
									  final GenerateFunctions aGenerateFunctions) {
		mcep = aMcep;
		mt = aMt;
		gf = aGenerateFunctions;
	}

	@Override
	public void generate(final IClassGenerator dcg) {
        final @NotNull ClassStatement cs = mcep.getKlass();
        final FunctionDef               f  = mcep.getMainFunction();
        final @Nullable ClassInvocation ci = dcg.registerClassInvocation(cs, null);

		dcg.submitGenerateClass(Objects.requireNonNull(ci), gf);

		final @NotNull FunctionInvocation fi = dcg.newFunctionInvocation(f, null, ci);
//		fi.setPhase(deducePhase);
		dcg.submitGenerateFunction(fi, gf);

		mt.describe(new ModuleThing.GeneralDescription(new ES_Symbol("MainClassEntryPoint"),tripleo.elijah_fluffy.util.Helpers.List_of(ci, fi)));
	}
}
