package tripleo.elijah.javac_model.lang.model.util;

import tripleo.elijah.javac_model.lang.model.element.*;

import javax.annotation.processing.*;

import static javax.lang.model.SourceVersion.*;

@SupportedSourceVersion(RELEASE_7)
public class SimpleElementVisitor7<R, P> extends SimpleElementVisitor6<R, P> {
	@Deprecated(since = "12")
	protected SimpleElementVisitor7() {
		super(null); // Superclass constructor deprecated too
	}

	@Deprecated(since = "12")
	protected SimpleElementVisitor7(R defaultValue) {
		super(defaultValue); // Superclass constructor deprecated too
	}

	@Override
	public R visitVariable(VariableElement e, P p) {
		return defaultAction(e, p);
	}
}
