package tripleo.elijah_durable_elevated.stages.deduce;

import lombok.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_durable_elevated.stages.gen_fn.*;

//@Getter
public class DeduceCentral {
	private final DeduceTypes2 deduceTypes2;

	public DeduceCentral(final DeduceTypes2 aDeduceTypes2) {
		deduceTypes2 = aDeduceTypes2;
	}

	public @NotNull DC_ClassNote note_Class(final ClassStatement aE, final Context aCtx) {
		//noinspection UnnecessaryLocalVariable
		DC_ClassNote cn = deduceTypes2._inj().new_DC_ClassNote(aE, aCtx, this);
		return cn;
	}

	public static class DC_ClassNote {
		private final DeduceCentral    central;
		private final Context          ctx;
		private final ClassStatement   e;
		private       DC_ClassNote_DT2 dc_classNote_dt2;

		public DC_ClassNote(final ClassStatement aE, final Context aCtx, final DeduceCentral aCentral) {
			e       = aE;
			ctx     = aCtx;
			central = aCentral;
		}

		public void attach(final IdentTableEntry aIte, final BaseEvaFunction aGeneratedFunction) {
			final DeduceTypes2 deduceTypes2 = central.getDeduceTypes2();
			dc_classNote_dt2 = deduceTypes2._inj().new_DC_ClassNote_DT2(aIte, aGeneratedFunction, deduceTypes2);
		}

		@Getter
		static class DC_ClassNote_DT2 {
			private final DeduceTypes2    deduceTypes2;
			private final BaseEvaFunction generatedFunction;
			private final IdentTableEntry ite;

			public DC_ClassNote_DT2(final IdentTableEntry aIte,
									final BaseEvaFunction aGeneratedFunction,
									final DeduceTypes2 aDeduceTypes2) {
				ite               = aIte;
				generatedFunction = aGeneratedFunction;
				deduceTypes2      = aDeduceTypes2;
			}

		}
	}

	private DeduceTypes2 getDeduceTypes2() {
		return this.deduceTypes2;
	}

}
