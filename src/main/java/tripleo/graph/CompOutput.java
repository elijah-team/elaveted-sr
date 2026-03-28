package tripleo.graph;

import tripleo.elijah.nextgen.outputstatement.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.small.*;

import java.util.*;

public interface CompOutput {
	int countMarkers();

	CK_Marker getMarker(int index);

	CK_Marker getMarker(String aPath);

	// markers
	List<CK_Marker> listMarkers();

	// logs
	Cursor<CK_Log> perFile(CE_Path p);

	// outputTree
	void writeToPath(CE_Path p, EG_Statement stmt);

	Eventual<CompSnapshot> getSnapshot(ES_Symbol aSnapshotSymbol);

	record CompSnapshot(
			List<CK_Marker> markers,
			List<CK_Log> logs,
			List<CK_Fragment> fragments
	) {
	}
}
