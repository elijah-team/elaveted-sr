package tripleo.elijah_durable_elevated.stages.gen_generic;

public interface IGenerateResultWatcher {
	void complete();

	void item(GenerateResultItem item);
}
