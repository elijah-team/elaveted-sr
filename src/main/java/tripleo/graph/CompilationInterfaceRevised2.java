package tripleo.graph;

public interface CompilationInterfaceRevised2 {
	CK_Marker addMarker(String aPath, Object aValue);

	CompOutput getOutput();

	CompilationInterfaceRevised getRevised();

	CK_Marker getMarker(String aPath);
}
