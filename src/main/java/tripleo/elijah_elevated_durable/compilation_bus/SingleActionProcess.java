package tripleo.elijah_elevated_durable.compilation_bus;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.i.*;

import java.util.*;

public class SingleActionProcess implements CB_Process {
	// README tape
	private final CB_Action a;
	private final String    name;

	public SingleActionProcess(final CB_Action aProcessAction, final String aProcessName) {
		a    = aProcessAction;
		name = aProcessName;
	}

	@Override
	public @NotNull List<CB_Action> steps() {
		return tripleo.elijah_fluffy.util.Helpers.List_of(a);
	}

	@Override
	public String name() {
		return name;//"SingleActionProcess";
	}
}
