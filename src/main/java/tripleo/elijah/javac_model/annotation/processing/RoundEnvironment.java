package tripleo.elijah.javac_model.annotation.processing;

import tripleo.elijah.javac_model.lang.model.element.*;

import java.lang.annotation.*;
import java.util.*;

public interface RoundEnvironment {
	boolean processingOver();

	boolean errorRaised();

	Set<? extends Element> getRootElements();

	default Set<? extends Element> getElementsAnnotatedWithAny(TypeElement... annotations) {
		// Use LinkedHashSet rather than HashSet for predictability
		Set<Element> result = new LinkedHashSet<>();
		for (TypeElement annotation : annotations) {
			result.addAll(getElementsAnnotatedWith(annotation));
		}
		return Collections.unmodifiableSet(result);
	}

	Set<? extends Element> getElementsAnnotatedWith(TypeElement a);

	default Set<? extends Element> getElementsAnnotatedWithAny(Set<Class<? extends Annotation>> annotations) {
		// Use LinkedHashSet rather than HashSet for predictability
		Set<Element> result = new LinkedHashSet<>();
		for (Class<? extends Annotation> annotation : annotations) {
			result.addAll(getElementsAnnotatedWith(annotation));
		}
		return Collections.unmodifiableSet(result);
	}

	Set<? extends Element> getElementsAnnotatedWith(Class<? extends Annotation> a);
}
