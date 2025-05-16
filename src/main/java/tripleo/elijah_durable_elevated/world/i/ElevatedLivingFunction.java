package tripleo.elijah_durable_elevated.world.i;

import org.jdeferred2.DoneCallback;
import tripleo.elijah.world.i.LivingFunction;
import tripleo.elijah_durable_elevated.stages.gen_fn.BaseEvaFunction;

public interface ElevatedLivingFunction extends LivingFunction {
	//void offer(AmazingPart aAp);

	BaseEvaFunction evaNode();

	void codeRegistration(LF_CodeRegistration acr);

	boolean isRegistered();

	void listenRegister(DoneCallback<Integer> aCodeCallback);
}
