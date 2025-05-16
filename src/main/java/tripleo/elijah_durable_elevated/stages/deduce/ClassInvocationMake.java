package tripleo.elijah_durable_elevated.stages.deduce;

import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_durable_elevated.stages.gen_fn.*;
import tripleo.elijah_fluffy.util.*;

import java.util.*;

public enum ClassInvocationMake {
	;

	public static @NotNull Operation<ClassInvocation> withGenericPart(@NotNull ClassStatement best,
																	  String constructorName, @Nullable NormalTypeName aTyn1, @NotNull DeduceTypes2 dt2) {
		if (aTyn1 == null) {
			// throw new IllegalStateException("blank typename");
		}

		@NotNull
		GenericPart genericPart = dt2._inj().new_GenericPart(best, aTyn1);

		@Nullable
		ClassInvocation clsinv = dt2._inj().new_ClassInvocation(best, constructorName, new ReadySupplier_1<>(dt2));

		if (genericPart.hasGenericPart()) {
			final @NotNull List<TypeName> gp  = best.getGenericPart();
			final @Nullable TypeNameList  gp2 = genericPart.getGenericPartFromTypeName();

			if (gp2 != null) {
				for (int i = 0; i < gp.size(); i++) {
					final TypeName typeName = gp2.get(i);
					@NotNull
					GenType typeName2;
					try {
						typeName2 = dt2.resolve_type(dt2._inj().new_OS_UserType(typeName), typeName.getContext());
						// TODO transition to GenType
						clsinv.set(i, gp.get(i), typeName2.getResolved());
					} catch (ResolveError aResolveError) {
						return Operation.failure(aResolveError);
					}
				}
			}
		}
		return Operation.success(clsinv);
	}
}
