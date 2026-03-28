package tripleo.graph;

public interface CK_Marker {
	default String getPath() {
		return null;
	}

	default CK_MarkerType getMarkerType() {
		return CK_MarkerType.UNKNOWN;
	}

	default Object getValue() {
		return null;
	}

	/// forgot we can do -1
	default int fixCount() {
		return -1;
	}

	/// If we return true, then ???
	default boolean fix(int index) {
		return false;
	}

	public enum CK_MarkerType {
		/**
		 * For things that appear in the program text
		 */
		CODE,
		/**
		 * These push to EOT_OutputFile#[type=LOG]
		 */
		LOG,
		/**
		 * Replacing/implementing logProgress
		 */
		PROGRESS,
		PROCESS, // !!
		UNKNOWN // is this one duh??
	}
}
