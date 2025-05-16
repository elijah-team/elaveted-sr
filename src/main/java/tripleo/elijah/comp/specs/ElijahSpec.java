package tripleo.elijah.comp.specs;

import tripleo.elijah.compiler_model.*;
import tripleo.wrap.File;

import java.io.*;

public interface ElijahSpec {
	String getLongPath2();

	String file_name();

	File file();

	InputStream s();

	CM_Module getModule();
}
