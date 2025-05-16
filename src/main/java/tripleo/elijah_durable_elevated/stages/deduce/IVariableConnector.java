package tripleo.elijah_durable_elevated.stages.deduce;

import tripleo.elijah_durable_elevated.stages.gen_fn.*;

import java.util.*;

interface IVariableConnector {
	void connect(VariableTableEntry aVte, String aName);

	static class CtorConnector implements IVariableConnector {
		private final IEvaConstructor evaConstructor;
		private final DeduceTypes2    deduceTypes2;

		public CtorConnector(final IEvaConstructor aEvaConstructor, final DeduceTypes2 aDeduceTypes2) {
			evaConstructor = aEvaConstructor;
			deduceTypes2   = aDeduceTypes2;
		}

		@Override
		public void connect(final VariableTableEntry aVte, final String aName) {
			PromiseExpectation<EvaClass> pe_evaClass = deduceTypes2._inj().new_PromiseExpectation(evaConstructor, "evaConstructor.onGenClass", deduceTypes2);

			evaConstructor.onGenClass((EvaClass aGeneratedClass) -> {
				pe_evaClass.satisfy(aGeneratedClass);

				final List<EvaContainer.VarTableEntry> vt = (aGeneratedClass).varTable;

				for (EvaContainer.VarTableEntry gc_vte : vt) {
					if (gc_vte.nameToken.getText().equals(aName)) {
						gc_vte.connect(aVte, evaConstructor);
						break;
					}
				}
			});
		}
	}

	static class NullConnector implements IVariableConnector {
		@Override
		public void connect(final VariableTableEntry aVte, final String aName) {
		}
	}
}
