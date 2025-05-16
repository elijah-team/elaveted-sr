package tripleo.elijah.ci;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;

public interface CiExpressionList {
	void add(CiExpression aExpr);

	@NotNull Collection<CiExpression> expressions();

	@NotNull Iterator<CiExpression> iterator();

	@NotNull CiExpression next(/*@NotNull*/ CiExpression aExpr);

	int size();

	@Override
	String toString();
}
