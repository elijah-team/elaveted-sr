package tripleo.elijah_durable_elevated.stages.deduce;

import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_durable_elevated.lang.impl.*;
import tripleo.elijah_durable_elevated.stages.deduce.declarations.*;
import tripleo.elijah_durable_elevated.stages.gen_fn.*;

import java.util.*;

public record DeduceElementWrapper(OS_Element element) {
	public boolean isNamespaceStatement() {
		return element instanceof NamespaceStatement;
	}

	@NotNull
	public DeferredMember deferred_member(final /* @NotNull */ IInvocation aInvocation, final @NotNull VariableStatementImpl aVariableStatement,
										  final @NotNull IdentTableEntry ite, final DeduceTypes2 aDeduceTypes2) {
		@NotNull
		DeferredMember dm = Objects.requireNonNull(deferred_member(aInvocation, aVariableStatement, aDeduceTypes2));
		dm.externalRef().then(ite::setExternalRef);
		return dm;
	}

	private @NotNull DeferredMember deferred_member(@Nullable IInvocation aInvocation, VariableStatementImpl aVariableStatement, final DeduceTypes2 aDeduceTypes2) {
		final IInvocation    invocation = _deferred_member_invocation(aInvocation, aDeduceTypes2);
		final DeferredMember dm         = aDeduceTypes2._inj().new_DeferredMember(this, invocation, aVariableStatement);
		aDeduceTypes2.phase.addDeferredMember(dm);
		DebugPrint.addDeferredMember(dm);
		return dm;
	}

	private IInvocation _deferred_member_invocation(final @Nullable IInvocation aInvocation, final DeduceTypes2 aDeduceTypes2) {
		final IInvocation invocation;
		if (aInvocation == null) {
			if (isNamespaceStatement()) {
				invocation = aDeduceTypes2.phase.registerNamespaceInvocation((NamespaceStatement) element());
			} else if (element() instanceof ClassStatement cs) {
				invocation = aDeduceTypes2._inj().new_ClassInvocation(cs, null, () -> aDeduceTypes2);
			} else
				throw new IllegalStateException("bad invocation");
		} else {
			invocation = aInvocation;
		}
		return invocation;
	}

/*
	@Override
	public String toString() {
		return "DeduceElementWrapper[" +
				"element=" + element + ']';
	}
*/
}
