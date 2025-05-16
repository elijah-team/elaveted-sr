package tripleo.elijah_elevated_durable.comp;

import tripleo.elijah.comp.specs.*;
import tripleo.elijah.compiler_model.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_elevated_durable.compiler_model.*;
import tripleo.elijah_fluffy.util.*;

import java.util.*;

final class _A_megaGrande {
	private final Map<ElijahSpec, CM_Module> specToModuleMap;
	private final Map<EzSpec, CM_Ez>         specToEzMap;
	private final Map<OS_Module, CM_Module>  moduleToCMMap;

	_A_megaGrande() {
		specToModuleMap = new HashMap<>();
		specToEzMap     = new HashMap<>();
		moduleToCMMap   = new HashMap<>();
	}

	public CM_Module megaGrande(final ElijahSpec aSpec, final Operation2<OS_Module> aModuleOperation) {
		CM_Module result;
		if (specToModuleMap.containsKey(aSpec)) {
			result = specToModuleMap.get(aSpec);
		} else {
			assert aModuleOperation.mode() == Mode.SUCCESS;
			result = megaGrande(aModuleOperation.success());
			specToModuleMap.put(aSpec, result);
		}

		result.advise(aSpec);
		//result.advise(aModuleOperation);

		return result;
	}

	public CM_Module megaGrande(final OS_Module aModule) {
		CM_Module result;
		if (moduleToCMMap.containsKey(aModule)) {
			result = moduleToCMMap.get(aModule);
		} else {
			result = new EDL_CM_Module();
			result.set(aModule);
			moduleToCMMap.put(aModule, result);
		}

		result.advise(Operation2.success(aModule));

		return result;
	}

	public CM_Ez megaGrande(final EzSpec aSpec) {
		CM_Ez result;
		if (specToEzMap.containsKey(aSpec)) {
			result = specToEzMap.get(aSpec);
		} else {
			//assert aModuleOperation.mode() == Mode.SUCCESS;
			//result = megaGrande(aModuleOperation.success());
			result = new EDL_CM_Ez();
			specToEzMap.put(aSpec, result);
		}

		result.advise(aSpec);
		//result.advise(aModuleOperation);

		return result;
	}
}
