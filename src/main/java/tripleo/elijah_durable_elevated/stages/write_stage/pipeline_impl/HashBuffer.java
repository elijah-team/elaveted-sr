package tripleo.elijah_durable_elevated.stages.write_stage.pipeline_impl;

import org.jetbrains.annotations.*;
import tripleo.elijah_durable_elevated.stages.gen_generic.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.util.buffer.*;

/*
 * intent: HashBuffer
 *  - contains 3 sub-buffers: hash, space, and filename
 *  - has all logic to update and present hash
 *    - codec: MTL sha2 here
 *    - encoding: reg or multihash (hint hint...)
 */
public class HashBuffer extends DefaultBuffer {
	final DoubleLatch<String> dl;

	private final @Nullable HashBufferList parent;

	public HashBuffer(final String string) {
		super(string);

		parent = null;

		final HashBuffer outputBuffer = this;

		dl = new DoubleLatch<>(aFilename -> {
			final @NotNull Operation<String> hh2 = Helpers.getHashForFilename(aFilename);

			switch (hh2.mode()) {
			case SUCCESS -> {
				final @NotNull String hh = hh2.success();

				if (hh != null) {
					outputBuffer.append(hh);
					outputBuffer.append(" ");
					outputBuffer.append_ln(aFilename);
				}
			}
			default -> throw new RuntimeException(hh2.failure());
			}
		});

		dl.notifyData(string);
	}

	// public String getText() {
	// dl.notifyLatch(true);
	//
	// return dl.
	// }

/*
	public HashBuffer(final String aFileName, final HashBufferList aHashBufferList, final Executor aExecutor) {
		super("");

		parent = aHashBufferList;
		// parent.setNext(this);

		dl = new DoubleLatch<>(aFilename -> {
			final HashBuffer outputBuffer = this;

			final @NotNull Operation<String> hh2 = Helpers.getHashForFilename(aFilename);

			if (hh2.mode() == Mode.SUCCESS) {
				final @NotNull String hh = hh2.success();

				if (hh != null) {
					outputBuffer.append(hh);
					outputBuffer.append(" ");
					outputBuffer.append_ln(aFilename);
				}
			} else {
				throw new RuntimeException(hh2.failure());
			}
		});
		dl.notifyData(aFileName);
	}
*/
}
