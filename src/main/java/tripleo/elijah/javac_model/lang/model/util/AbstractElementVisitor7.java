package tripleo.elijah.javac_model.lang.model.util;

import javax.annotation.processing.*;

import static javax.lang.model.SourceVersion.*;


@SupportedSourceVersion(RELEASE_7)
public abstract class AbstractElementVisitor7<R, P> extends AbstractElementVisitor6<R, P> {
	@Deprecated(since = "12")
	protected AbstractElementVisitor7() {
		super(); // Superclass constructor deprecated too
	}
}
