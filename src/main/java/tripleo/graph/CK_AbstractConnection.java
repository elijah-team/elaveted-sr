package tripleo.graph;

import tripleo.small.*;
import tripleo.util.io.*;

import java.util.*;

/// This is ugly as hell and has nothing to do with the other one
public interface CK_AbstractConnection {

	boolean isOpen();

	// !
	void add(ES_Symbol name, CharSource chars);

	boolean has(ES_Symbol name);

	ES_Symbol hashName(String name);

	// why is this confusing??
	Map<String, ES_Symbol> list(ES_Symbol dirname);
}
