package tripleo.elijah.nextgen;

import org.jetbrains.annotations.*;
import tripleo.elijah.g.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.composable.*;

import java.util.*;

public interface ClassDefinition {
	IComposable getComposable();

	void setComposable(IComposable aComposable);

	@NotNull Set<ClassStatement> getExtended();

	GClassInvocation getInvocation();

	void setInvocation(GClassInvocation aInvocation);

	GEvaClass getNode();

	void setNode(GEvaClass aNode);

	ClassStatement getPrimary();
}
