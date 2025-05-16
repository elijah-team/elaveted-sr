package tripleo.paths;

import java.io.PrintStream;

public interface AsseverationLogProgress {
	void call(PrintStream out, PrintStream err);
}
