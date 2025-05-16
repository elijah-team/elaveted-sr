package tripleo.elijah.javac_model.lang.model.util;

import tripleo.elijah.javac_model.lang.model.type.*;

import javax.annotation.processing.*;

import static javax.lang.model.SourceVersion.*;

@SupportedSourceVersion(RELEASE_14)
public class TypeKindVisitor9<R, P> extends TypeKindVisitor8<R, P> {
	protected TypeKindVisitor9() {
		super(null);
	}

	protected TypeKindVisitor9(R defaultValue) {
		super(defaultValue);
	}

	@Override
	public R visitNoTypeAsModule(NoType t, P p) {
		return defaultAction(t, p);
	}
}
