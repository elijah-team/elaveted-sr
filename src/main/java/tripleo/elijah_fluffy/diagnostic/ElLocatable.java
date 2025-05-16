package tripleo.elijah_fluffy.diagnostic;

/*
 * Renamed because too common of a name (javax.compiler, blah blah)
 * Autocomplete gets annoying
 */
public interface ElLocatable {
	boolean isSpan();

	boolean isQuery();

	enum LocatableKind {
		QuerySearchEzFiles {
			@Override
			public boolean isQuery() {
				return true;
			}

			@Override
			public boolean isSpan() {
				return false;
			}
		}, TextSpan {
			@Override
			public boolean isQuery() {
				return false;
			}

			@Override
			public boolean isSpan() {
				return true;
			}
		};

		public abstract boolean isQuery();

		public abstract boolean isSpan();
	}
}
