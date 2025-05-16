package tripleo.elijah_elevated_durable.comp;

import tripleo.elijah_durable_elevated.comp.i.extra.*;

/**
 * This one's interesting
 */
public interface EDL_SPI_PipelineAccessT {
	static EDL_SPI_PipelineAccessT of(IPipelineAccess aPa) {
		return new EDL_SPI_PipelineAccessT() {
			@Override
			public IPipelineAccess get() {
				return aPa;
			}
		};
	}

	IPipelineAccess get();
}
