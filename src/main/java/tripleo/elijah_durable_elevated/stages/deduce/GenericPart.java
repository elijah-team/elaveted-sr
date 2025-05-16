package tripleo.elijah_durable_elevated.stages.deduce;

import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;

class GenericPart {
	private final ClassStatement classStatement;
	private final TypeName       genericTypeName;

	@Contract(pure = true)
	public GenericPart(final ClassStatement aClassStatement, final TypeName aGenericTypeName) {
		classStatement  = aClassStatement;
		genericTypeName = aGenericTypeName;
	}

	@Contract(pure = true)
	public @Nullable TypeNameList getGenericPartFromTypeName() {
		final NormalTypeName ntn = getGenericTypeName();
		if (ntn == null)
			return null;
		return ntn.getGenericPart();
	}

	@Contract(pure = true)
	private @Nullable NormalTypeName getGenericTypeName() {
		// assert genericTypeName != null;
		if (genericTypeName == null) {

			tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon.println_err_4("1860 who cares // assert genericTypeName != null");

		}
		/*
		 * for (boolean aB : _inj().new_boolean[]{genericTypeName != null,
		 * genericTypeName instanceof NormalTypeName}) { assert aB; }
		 */

		return (NormalTypeName) genericTypeName;
	}

	@Contract(pure = true)
	public boolean hasGenericPart() {
		return !classStatement.getGenericPart().isEmpty();
	}
}
