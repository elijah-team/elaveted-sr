package tripleo.elijah_fluffy.util;

public class ProgramIsLikelyWrong extends RuntimeException {
	public ProgramIsLikelyWrong(final String aCanNotHappen) {
		super(aCanNotHappen);
	}

	public ProgramIsLikelyWrong() {
		super();
	}
}
