package tripleo.elijah_elevated_durable.comp;

public class EDL_SPI_CompilationT {
	private final EDL_Compilation compilation;

	private EDL_SPI_CompilationT(final EDL_Compilation aCompilation) {
		compilation = aCompilation;
	}

	public static EDL_SPI_CompilationT of(final EDL_Compilation aCompilation) {
		return new EDL_SPI_CompilationT(aCompilation);
	}

	public EDL_Compilation getCompilation() {
		return compilation;
	}
}
