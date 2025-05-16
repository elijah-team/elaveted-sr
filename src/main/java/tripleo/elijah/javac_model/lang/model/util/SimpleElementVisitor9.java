package tripleo.elijah.javac_model.lang.model.util;

import tripleo.elijah.javac_model.lang.model.element.*;

import javax.annotation.processing.*;

import static javax.lang.model.SourceVersion.*;

@SupportedSourceVersion(RELEASE_14)
public class SimpleElementVisitor9<R, P> extends SimpleElementVisitor8<R, P> {
	protected SimpleElementVisitor9() {
		super(null);
	}

	protected SimpleElementVisitor9(R defaultValue) {
		super(defaultValue);
	}

	@Override
	public R visitModule(ModuleElement e, P p) {
		return defaultAction(e, p);
	}
}
