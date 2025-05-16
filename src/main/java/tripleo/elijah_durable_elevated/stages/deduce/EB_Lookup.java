package tripleo.elijah_durable_elevated.stages.deduce;

import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_durable_elevated.lang.impl.*;
import tripleo.elijah_elevated_durable.lang_model.*;
import tripleo.elijah_fluffy.diagnostic.*;
import tripleo.elijah_fluffy.util.*;

public class EB_Lookup {
	private final          Eventual<OS_Element> ev = new Eventual<>();
	private final @NotNull DeduceTypes2         deduceTypes2;
	private                Context              context;
	private                LookupResultList     lrl;
	private                String               lookupName;

    public EB_Lookup(final DeduceTypes2 aDt2, final Context aContext, final String aName) {
        this(aDt2);
        setContext(aContext);
        doLookup(aName);
        assert ev != null;
    }

    public EB_Lookup(final @NotNull DeduceTypes2 aDeduceTypes2) {
        deduceTypes2 = aDeduceTypes2;
    }

    public void setContext(final Context aContext) {
        context = aContext;
    }

    public LookupResultList doLookup(final String aName) {
        this.lookupName = aName;
        lrl = context.lookup(aName);
        return lrl;
    }

    public Eventual<OS_Element> emptyChooseBestResolvingAliases() {
        @Nullable OS_Element best = lrl.chooseBest(null);

        while (best instanceof AliasStatement) {
            try {
                final AliasStatementImpl aliasStatement = (AliasStatementImpl) best;
                final @NotNull EN_Name   aliasName      = aliasStatement.getEnName();
                best = DeduceLookupUtils._resolveAlias2(aliasStatement, deduceTypes2);
            } catch (ResolveError aE) {
                ev.reject((Diagnostic) aE);
                return ev;
            }
        }

        if (best == null) {
            final ResolveError d = new ResolveError(IdentExpressionImpl.forString(this.lookupName), lrl);
            ev.reject((Diagnostic) d);
            return ev;
        }

        return ev;
    }

    public Eventual<OS_Element> getEventual() {
        return ev;
    }
}
