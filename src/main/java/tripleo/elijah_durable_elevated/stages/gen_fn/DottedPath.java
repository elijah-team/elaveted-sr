package tripleo.elijah_durable_elevated.stages.gen_fn;

import tripleo.elijah.lang.i.*;

import java.util.*;

public interface DottedPath {
    String asString();

    List<String> asList();

    List<OS_Element> asElementList();

    List<DT_Element> asDtElementList();
}
