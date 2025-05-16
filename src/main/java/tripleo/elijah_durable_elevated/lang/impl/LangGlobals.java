package tripleo.elijah_durable_elevated.lang.impl;

import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_durable_elevated.util.*;

public enum LangGlobals {
	;
	public static final IExpression     UNASSIGNED           = new BasicBinaryExpressionImpl() {
		@Override
		public @NotNull String toString() {
			return "<UNASSIGNED expression>";
		}
	};
	public final static IdentExpression emptyConstructorName = Helpers0.string_to_ident("<>");
	// TODO override name() ??
	public final static ConstructorDef  defaultVirtualCtor   = new ConstructorDefImpl(null, null, null);
	// TODO Living?
	public static final OS_Package      default_package      = new OS_PackageImpl(null, 0);
	private static final OS_Package _dp = new OS_PackageImpl(null, 0);

	public static boolean isConstant(final IExpression expression) {
		return expression instanceof StringExpression || expression instanceof CharLitExpression
				|| expression instanceof FloatExpression || expression instanceof NumericExpression;
	}

	public static @NotNull OS_Package defaultPackage() {
		return _dp;
	}

	// FIXME question placement
	@Contract("_ -> new")
	public static @NotNull IdentExpression identFromString(final String string) {
		return new IdentExpressionImpl(Helpers0.makeToken(string), "<inline-absent2>");
	}
}
