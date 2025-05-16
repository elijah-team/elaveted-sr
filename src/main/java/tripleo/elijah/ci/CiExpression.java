package tripleo.elijah.ci;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_fluffy.util.*;

public interface CiExpression {
	@NotNull CiExpressionKind getKind();

	void setKind(CiExpressionKind aCiExpressionKind);

	CiExpression getLeft();

	void setLeft(CiExpression iexpression);

	boolean is_simple();

	default String printableString() {
		throw new UnintendedUseException();
	}

	String repr_();

	@Override
	String toString();
}
