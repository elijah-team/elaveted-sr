package tripleo.graph;

import org.apache.commons.lang3.tuple.Pair;
import tripleo.elijah.comp.CompilerInput;

import java.util.List;

public interface CompilationInterfaceRevised {

	CK_Marker addMarker(String aPath, CK_Marker.CK_MarkerType aMarkerType, Object aValue);

	CirResult compile(List<CompilerInput> lci);

	default int errorCount() {
		throw new RuntimeException("too complicated.");
	}

	public interface CirResult {
		CompOutput getOutput();

		CompInteractive getInteractive();

		CK_Marker getMarker(String aPath);

		int markerCount();

		int errorCount();
	}
}
