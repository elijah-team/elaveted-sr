package tripleo.elijah.lang.i;

import tripleo.elijah_fluffy.diagnostic.*;

import java.io.*;

public interface ListExpression extends IExpression, TextLocatable {
	@Override
	int getColumn();

	@Override
	int getColumnEnd();

	@Override
	File getFile();

	@Override
	int getLine();

	@Override
	int getLineEnd();

	@Override
	boolean is_simple();

	void setContents(ExpressionList aList);
}
