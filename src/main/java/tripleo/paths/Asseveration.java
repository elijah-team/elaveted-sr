package tripleo.paths;

import tripleo.graph.*;

public interface Asseveration {
	Object target();

	Asseverate code();

	void onLogProgress(Asseverable ce);

}
