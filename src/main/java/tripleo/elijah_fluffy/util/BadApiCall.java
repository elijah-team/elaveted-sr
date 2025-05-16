package tripleo.elijah_fluffy.util;

public class BadApiCall extends RuntimeException {
	public BadApiCall(final String message) {
		super(message);
	}
}
