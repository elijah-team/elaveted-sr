package tripleo.elijah.javac_model.lang.model.util;

import tripleo.elijah.javac_model.lang.model.element.*;

import javax.annotation.processing.*;

import static javax.lang.model.SourceVersion.*;


@SupportedSourceVersion(RELEASE_14)
public abstract class AbstractElementVisitor9<R, P> extends AbstractElementVisitor8<R, P> {
	protected AbstractElementVisitor9() {
		super();
	}

	@Override
	public abstract R visitModule(ModuleElement e, P p);
}
