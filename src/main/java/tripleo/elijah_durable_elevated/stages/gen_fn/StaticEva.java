package tripleo.elijah_durable_elevated.stages.gen_fn;

import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_durable_elevated.stages.deduce.*;
import tripleo.elijah_durable_elevated.stages.instructions.*;

import java.util.*;

public enum StaticEva {
	;

	static void printTables(@NotNull EvaFunction gf) {
		tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon.println_out_2("VariableTable ");
		for (VariableTableEntry variableTableEntry : gf.vte_list) {
			tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon.println_out_2("\t" + variableTableEntry);
		}
		tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon.println_out_2("ConstantTable ");
		for (ConstantTableEntry constantTableEntry : gf.cte_list) {
			tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon.println_out_2("\t" + constantTableEntry);
		}
		tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon.println_out_2("ProcTable     ");
		for (ProcTableEntry procTableEntry : gf.prte_list) {
			tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon.println_out_2("\t" + procTableEntry);
		}
		tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon.println_out_2("TypeTable     ");
		for (TypeTableEntry typeTableEntry : gf.tte_list) {
			tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon.println_out_2("\t" + typeTableEntry);
		}
		tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon.println_out_2("IdentTable    ");
		for (IdentTableEntry identTableEntry : gf.idte_list) {
			tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon.println_out_2("\t" + identTableEntry);
		}
	}

	public static @NotNull List<DT_Resolvable> _getIdentIAResolvableList(@NotNull InstructionArgument oo) {
		LinkedList<DT_Resolvable> R = new LinkedList<>();
		while (oo != null) {
			if (oo instanceof IntegerIA integerIA) {
				var vte = integerIA.getEntry();

				if (vte._vs == null) {
					final OS_Element[] el = {null};
					vte._p_elementPromise.then(el1 -> el[0] = el1);

					assert el[0] != null;

					R.addFirst(DT_Resolvable.from(oo, el[0], null));
				} else {
					R.addFirst(DT_Resolvable.from(oo, vte._vs, null));
				}
				oo = null;
			} else if (oo instanceof final IdentIA identIA) {
				final IdentTableEntry ite1 = identIA.getEntry();

				final OS_Element[] el = {null};
				ite1._p_resolvedElementPromise.then(el1 -> el[0] = el1);

				// assert el[0] != null;

				FunctionInvocation cfi = null;
				if (ite1._callable_pte() != null) {
					var cpte = ite1._callable_pte();
					if (cpte.getFunctionInvocation() != null) {
						cfi = cpte.getFunctionInvocation();
					}
				}

				// assert cfi != null;
				// ^^ fails for folders.forEach

				R.addFirst(DT_Resolvable.from(oo, el[0], cfi));
				oo = ite1.getBacklink();
			} else if (oo instanceof ProcIA procIA) {
				var pte = procIA.getEntry();
				assert pte != null;

				final OS_Element[] el = {null};
				pte._p_elementPromise.then(el1 -> el[0] = el1);

				assert el[0] != null;

				FunctionInvocation cfi = null;
				if (pte.getFunctionInvocation() != null) {
					cfi = pte.getFunctionInvocation();
				}

				assert cfi != null;

				R.addFirst(DT_Resolvable.from(oo, el[0], cfi));
				oo = null;
			} else
				throw new IllegalStateException("Invalid InstructionArgument");
		}
		return R;
	}

	public static @NotNull List<InstructionArgument> _getIdentIAPathList(@NotNull InstructionArgument oo) {
		LinkedList<InstructionArgument> s = new LinkedList<InstructionArgument>();
		while (oo != null) {
			if (oo instanceof IntegerIA) {
				s.addFirst(oo);
				oo = null;
			} else if (oo instanceof IdentIA) {
				final IdentTableEntry ite1 = ((IdentIA) oo).getEntry();
				s.addFirst(oo);
				oo = ite1.getBacklink();
			} else if (oo instanceof ProcIA) {
				s.addFirst(oo);
				oo = null;
			} else
				throw new IllegalStateException("Invalid InstructionArgument");
		}
		return s;
	}
}
