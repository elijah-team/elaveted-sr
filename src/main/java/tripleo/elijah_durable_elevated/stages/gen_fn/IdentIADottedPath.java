package tripleo.elijah_durable_elevated.stages.gen_fn;

import tripleo.elijah.lang.i.*;
import tripleo.elijah_durable_elevated.stages.instructions.*;
import tripleo.elijah_fluffy.util.*;

import java.util.*;

import static tripleo.elijah_durable_elevated.stages.deduce.DeduceTypes2.*;

public class IdentIADottedPath implements DottedPath {
	private final BaseEvaFunction           baseEvaFunction;
	private final IdentIA                   carrier;
	private final List<InstructionArgument> s;
	private       String                    calculated;

	public IdentIADottedPath(final BaseEvaFunction aBaseEvaFunction, final IdentIA aIdentIA, final List<InstructionArgument> aInstructionArgumentList) {
		baseEvaFunction = aBaseEvaFunction;
		carrier         = aIdentIA;
		s               = aInstructionArgumentList;
	}

	public IdentIADottedPath(final BaseEvaFunction aBaseEvaFunction, final IdentIA aIdentIA) {
		baseEvaFunction = aBaseEvaFunction;
		carrier         = aIdentIA;
		s               = StaticEva._getIdentIAPathList(carrier);
	}

	public String asString() {
		if (this.calculated == null) {
			//
			// TODO NOT LOOKING UP THINGS, IE PROPERTIES, MEMBERS
			//
			List<String> sl = new ArrayList<>();
			for (final InstructionArgument ia : s) {
				final String text;
				if (ia instanceof IntegerIA) {
					final VariableTableEntry vte = baseEvaFunction.getVarTableEntry(to_int(ia));
					text = vte.getName();
				} else if (ia instanceof IdentIA) {
					final IdentTableEntry idte = baseEvaFunction.getIdentTableEntry(((IdentIA) ia).getIndex());
					text = idte.getIdent().getText();
				} else if (ia instanceof ProcIA) {
					final ProcTableEntry prte = baseEvaFunction.getProcTableEntry(to_int(ia));
					assert prte.__debug_expression instanceof ProcedureCallExpression;
					text = ((ProcedureCallExpression) prte.__debug_expression).printableString();
				} else throw new NotImplementedException();
				sl.add(text);
			}
			this.calculated = Helpers.String_join(".", sl);
		}
		return calculated;
	}

	@Override
	public List<String> asList() {
		throw new UnintendedUseException("[24/09/29] implement me");
	}

	@Override
	public List<OS_Element> asElementList() {
		throw new UnintendedUseException("[24/09/29] implement me");
	}

	@Override
	public List<DT_Element> asDtElementList() {
		throw new UnintendedUseException("[24/09/29] implement me");
	}
}
