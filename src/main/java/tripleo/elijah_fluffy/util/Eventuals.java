package tripleo.elijah_fluffy.util;

public class Eventuals {
	public static <T> void resolveMaybe(final Eventual<T> aE, final T aValue) {
		if (aE.isPending()) aE.resolve(aValue);
	}
}
