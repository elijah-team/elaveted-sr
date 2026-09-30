package tripleo.elijah_fluffy.util;

public class ImplementMe extends RuntimeException {
	public ImplementMe() {
		int y=2;
	}

	public ImplementMe(final String message) {
		super(message);
	}

	public ImplementMe(final String message, final Throwable cause) {
		super(message, cause);
	}

	public ImplementMe(final Throwable cause) {
		super(cause);
	}

	public ImplementMe(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
