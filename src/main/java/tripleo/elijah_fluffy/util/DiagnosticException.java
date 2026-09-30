package tripleo.elijah_fluffy.util;

import tripleo.elijah_fluffy.diagnostic.*;

public class DiagnosticException extends Throwable {
	private final ElDiagnostic d;

	public DiagnosticException(final ElDiagnostic aD) {
		d = aD;
	}
}
