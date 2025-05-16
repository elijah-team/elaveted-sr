package tripleo.elijah_durable_elevated.world.i;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.g.GEvaClass;
import tripleo.elijah.g.GGarishClass;
import tripleo.elijah.g.GGenerateResult;
import tripleo.elijah.g.GGenerateResultSink;
import tripleo.elijah.world.i.LivingClass;
import tripleo.elijah_durable_elevated.stages.gen_generic.GenerateFiles;

public interface ElevatedLivingClass extends LivingNode, LivingClass {
	GEvaClass evaNode();

	GGarishClass getGarish();

	void setCode(int aCode);

	void generateWith(GGenerateResultSink aResultSink, @NotNull GGarishClass aGarishClass, GGenerateResult aGr, GenerateFiles aGenerateFiles);
}
