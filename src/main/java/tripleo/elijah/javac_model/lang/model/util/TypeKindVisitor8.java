package tripleo.elijah.javac_model.lang.model.util;

import tripleo.elijah.javac_model.lang.model.type.*;

import javax.annotation.processing.*;

import static javax.lang.model.SourceVersion.*;

@SupportedSourceVersion(RELEASE_8)
public class TypeKindVisitor8<R, P> extends TypeKindVisitor7<R, P> {
	@SuppressWarnings("deprecation") // Superclass constructor deprecated
	protected TypeKindVisitor8() {
		super(null);
	}

	@SuppressWarnings("deprecation") // Superclass constructor deprecated
	protected TypeKindVisitor8(R defaultValue) {
		super(defaultValue);
	}

	@Override
	public R visitIntersection(IntersectionType t, P p) {
		return defaultAction(t, p);
	}
}
