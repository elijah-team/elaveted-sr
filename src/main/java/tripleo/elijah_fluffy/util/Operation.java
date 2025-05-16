package tripleo.elijah_fluffy.util;

import org.jetbrains.annotations.*;
import tripleo.elijah_fluffy.diagnostic.*;

/**
 * An emulation of Rust's Result type
 *
 * @param <T> the success type
 */
public class Operation<T> /* extends Operation2<T> */ {
	public static <T> @NotNull Operation<T> failure(final Exception aException) {
		final Operation<T> op = new Operation<>(null, aException, Mode.FAILURE);
		return op;
	}

	public static <T> @NotNull Operation<T> success(final T aSuccess) {
		final Operation<T> op = new Operation<>(aSuccess, null, Mode.SUCCESS);
		return op;
	}

	private final Mode mode;

	private final T succ;

	private final Throwable exc;

	public Operation(final T aSuccess, final Throwable aException, final Mode aMode) {
		succ = aSuccess;
		exc = aException;
		mode = aMode;

		assert succ != exc;
	}

	public static <T> Operation<T> convert(final Operation2<T> aOperation2) {
		switch (aOperation2.mode()) {
		case FAILURE -> {
			final Diagnostic failure = aOperation2.failure();
			return Operation.failure(failure);
		}
		case SUCCESS -> {
			return Operation.success(aOperation2.success());
		}
		default -> throw new IllegalStateException("Unexpected value: " + aOperation2.mode());
		}
	}

	private static <T> Operation<T> failure(final Diagnostic aFailure) {
		return new Operation<>(null, new DiagnosticException(aFailure), Mode.FAILURE);
	}

	public Throwable failure() {
		return exc;
	}

	public @NotNull Mode mode() {
		return mode;
	}

	public T success() {
		return succ;
	}
}
