package tripleo.elijah_elevated_durable.comp;

public class EDL_SPI_CompilationEnclosureT {
	private final EDL_CompilationEnclosure compilationEnclosure;

	private EDL_SPI_CompilationEnclosureT(final EDL_CompilationEnclosure aCompilationEnclosure) {
		compilationEnclosure = aCompilationEnclosure;
	}

	public static EDL_SPI_CompilationEnclosureT of(final EDL_CompilationEnclosure aCompilation) {
		return new EDL_SPI_CompilationEnclosureT(aCompilation);
	}

	public EDL_CompilationEnclosure getCompilationEnclosure() {
		return compilationEnclosure;
	}
}
