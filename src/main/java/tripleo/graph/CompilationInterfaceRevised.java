package tripleo.graph;

import tripleo.elijah.comp.*;

import java.util.*;

public interface CompilationInterfaceRevised {

	CK_Marker addMarker(String aPath, CK_Marker.CK_MarkerType aMarkerType, Object aValue);

	void addMarker(CK_Marker aCKMarker);


	CirResult compile(List<CompilerInput> lci);

	default int errorCount() {
		throw new RuntimeException("too complicated.");
	}

	int markerCount();

	/// is this immutable (there is getSnapshot somewhere)
	public interface CirResult {
		CompOutput getOutput();

		CompInteractive getInteractive();

		CK_Marker getMarker(String aPath);

		int markerCount();

		int errorCount();
	}
}
