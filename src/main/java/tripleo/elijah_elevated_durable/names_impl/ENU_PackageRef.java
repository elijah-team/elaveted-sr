package tripleo.elijah_elevated_durable.names_impl;

import tripleo.elijah.lang.i.*;
import tripleo.elijah_elevated_durable.lang_model.*;

public class ENU_PackageRef implements EN_Understanding {
	private final OS_Package pkg;

	public ENU_PackageRef(final OS_Package aPkg) {
		pkg = aPkg;
	}

	public OS_Package getPackage() {
		return pkg;
	}
}
