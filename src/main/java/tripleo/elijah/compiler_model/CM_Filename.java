package tripleo.elijah.compiler_model;

import tripleo.elijah_durable_elevated.stages.deduce.fluffy.impl.*;
import tripleo.wrap.File;

import java.util.Objects;

public interface CM_Filename extends CM_Base {
	String getString();

	default File fileOf() {
		return new File(getString());
	}

	default String printableString() {
		return getString();
	}

	default boolean sameString(String string) {
		return Objects.equals(string, getString());
	}
}
