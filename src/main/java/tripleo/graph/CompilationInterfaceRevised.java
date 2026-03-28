package tripleo.graph;

import tripleo.elijah.comp.*;
import tripleo.elijah_elevated_durable.comp.*;

import java.util.*;

public interface CompilationInterfaceRevised {

	/// Try to hide this.
	int getState();

	CK_Marker addMarker(String aPath, CK_Marker.CK_MarkerType aMarkerType, Object aValue);

	void addMarker(CK_Marker aCKMarker);


	CirResult compile(List<CompilerInput> lci);

	default int errorCount() {
		throw new RuntimeException("too complicated.");
	}

	int markerCount();

	void advise(RevisedAdvisable aRevisedAdvisable);

	/// is this immutable (there is getSnapshot somewhere)
	public interface CirResult {
		CompOutput getOutput();

		CompInteractive getInteractive();

		CK_Marker getMarker(String aPath);

		int markerCount();

		int errorCount();
	}
}
