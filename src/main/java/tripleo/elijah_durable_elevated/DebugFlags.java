package tripleo.elijah_durable_elevated;

import java.io.*;

public class DebugFlags {
    public static final boolean MANUAL_DISABLED = false;
    public static final boolean _DefaultCompilationBus = true;
    /**
     * DONT change this
     */
    public static final boolean FORCE = true;
    /**
     * for you, Luke...
     */
    public static final boolean FORCE_IGNORE = false;

    public static final boolean NEVER_REACHED_BY_IDE = false;
    public static boolean Lawabidingcitizen_disabled = true;
    public static boolean CIS_ocp_ci__FeatureGate = true;
    public static boolean writeDumps = false;
    public static boolean CCI_gate = false;
    public static boolean MakeSense = true;
    public static boolean _pancake_lcm_gate = true;
    public static boolean youKnowBetter = true;
	public static boolean MakeSense2 = true;

	public static boolean isDebugging() {
        final File file = new File("is-debugging.flag");
        System.err.println("2525 " + file.getAbsolutePath());
        return file.exists();
	}
}
