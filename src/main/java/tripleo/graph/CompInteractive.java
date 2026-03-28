package tripleo.graph;

import tripleo.elijah.comp.*;

public interface CompInteractive {
	void addInput(CompilerInput aCompilerInput);

	void fixMarker(CK_Marker aMarker, int fixIndex); // sounds cool...

	// either a uuid/snowflake or ipfs thing
	String snapshot();
}
