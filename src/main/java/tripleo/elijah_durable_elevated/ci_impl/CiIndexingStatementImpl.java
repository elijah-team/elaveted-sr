package tripleo.elijah_durable_elevated.ci_impl;

import antlr.Token;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CiExpressionList;
import tripleo.elijah.ci.CiIndexingStatement;
import tripleo.elijah.ci.CompilerInstructions;

/**
 *
 * <p>
 * Created Apr 15, 2020 at 4:59:21 AM Created 1/8/21 7:19 AM
 */
@SuppressWarnings("FieldCanBeLocal")
public class CiIndexingStatementImpl implements CiIndexingStatement {
	private final @NotNull CompilerInstructions container;
	private                CiExpressionList     contained;
	private                Token                name;

	public CiIndexingStatementImpl(final @NotNull CompilerInstructions module) {
		this.container = module;
	}

	@Override
	public void setContained(final CiExpressionList el) {
		// back and forth
		this.contained = el;
	}

	@Override
	public void setName(final Token i1) {
		// back and forth
		this.name = i1;
	}
}
