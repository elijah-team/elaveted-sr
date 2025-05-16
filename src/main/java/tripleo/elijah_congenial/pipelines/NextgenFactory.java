package tripleo.elijah_congenial.pipelines;

import tripleo.elijah.nextgen.*;
import tripleo.elijah.nextgen.outputstatement.*;
import tripleo.paths.*;

public interface NextgenFactory {

	ER_Node createERNode(CP_Path aPp, EG_Statement aSeq);
}
