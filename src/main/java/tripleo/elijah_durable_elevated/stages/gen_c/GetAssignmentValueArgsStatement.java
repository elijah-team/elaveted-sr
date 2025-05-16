package tripleo.elijah_durable_elevated.stages.gen_c;

import org.jetbrains.annotations.*;
import tripleo.elijah.nextgen.outputstatement.*;
import tripleo.elijah_durable_elevated.stages.instructions.*;
import tripleo.elijah_fluffy.util.*;

import java.util.*;
import java.util.stream.*;

public class GetAssignmentValueArgsStatement implements EG_Statement {
	private final List<String> sll = new ArrayList<>();
	private final Instruction  inst;

	public GetAssignmentValueArgsStatement(final Instruction aInst) {
		inst = aInst;
	}

	public void add_string(final String aS) {
		sll.add(aS);
	}

	@Override
	public EX_Explanation getExplanation() {
		throw new UnintendedUseException();
		// return null;
	}

	@Override
	public @Nullable String getText() {
		final EG_SequenceStatement getAssignmentValueArgsStatement = new EG_SequenceStatement(
				new EG_Naming("GetAssignmentValueArgsStatement"),
				sll.stream()
						.map(x -> EG_Statement.of(x, null))
						.collect(Collectors.toList()
						)
		);
		return getAssignmentValueArgsStatement.getText();
	}

	public List<String> stringList() {
		return sll;
	}
}
