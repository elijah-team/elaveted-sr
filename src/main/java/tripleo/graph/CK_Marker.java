package tripleo.graph;

public interface CK_Marker {
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
		PROGRESS
	}
}
