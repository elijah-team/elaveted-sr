package tripleo.elijah_durable_elevated.stages.instructions;

import org.jetbrains.annotations.*;
import tripleo.elijah_fluffy.util.*;

public class InstructionFixedList implements IFixedList<InstructionArgument> {
	private final Instruction instruction;

	@Contract(pure = true)
	public InstructionFixedList(final Instruction aInstruction) {
		instruction = aInstruction;
	}

	@Override
	public InstructionArgument get(final int at) {
		return instruction.getArg(at);
	}

	@Override
	public int size() {
		return instruction.getArgsSize();
	}
}
