package tripleo.elijah_durable_elevated.stages.deduce.post_bytecode;

import tripleo.elijah.lang.i.*;
import tripleo.elijah_durable_elevated.stages.gen_fn.*;
import tripleo.elijah_fluffy.util.*;

/**
 * Also {@link DeduceType3}
 */
public interface DeduceElement3_Type {
	GenType genType();

	Operation2<GenType> resolved(Context ectx);

	TypeTableEntry typeTableEntry();
}
