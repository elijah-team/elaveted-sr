package tripleo.elijah.javac_model.lang.model.util;

import tripleo.elijah.javac_model.lang.model.type.*;

import javax.annotation.processing.*;

import static javax.lang.model.SourceVersion.*;

@SupportedSourceVersion(RELEASE_8)
public abstract class AbstractTypeVisitor8<R, P> extends AbstractTypeVisitor7<R, P> {
	@SuppressWarnings("deprecation") // Superclass constructor deprecated
	protected AbstractTypeVisitor8() {
		super();
	}

	@Override
	public abstract R visitIntersection(IntersectionType t, P p);
}
