package tripleo.elijah_durable_elevated.stages.write_stage.pipeline_impl;

import org.jdeferred2.impl.*;
import org.jetbrains.annotations.*;
import tripleo.graph.*;
import tripleo.elijah.comp.nextgen.pn.*;
import tripleo.elijah.nextgen.inputtree.*;
import tripleo.elijah.nextgen.outputstatement.*;
import tripleo.elijah.nextgen.outputtree.*;
import tripleo.elijah_durable_elevated.comp.*;
import tripleo.elijah_durable_elevated.comp.nextgen.pn.*;
import tripleo.elijah_durable_elevated.nextgen.outputtree.*;
import tripleo.elijah_durable_elevated.stages.gen_generic.*;
import tripleo.elijah_elevated_durable.comp.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.elijah_prolific.v.*;
import tripleo.paths.*;
import tripleo.wrap.File;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import tripleo.elijah_durable_elevated.work.*;

import static tripleo.elijah_fluffy.util.Helpers.List_of;

public class WPIS_WriteBuffers implements WP_Individual_Step, SC_I {
	private final WritePipeline                           writePipeline;
	private final Eventual<LSPrintStream.LSResult>             _p_spsp            = new Eventual<>();
	private final DeferredObject<EDL_ICompilation, Void, Void> compilationPromise = new DeferredObject<>();
	private final Bus                                          _m_bus             = new Bus();

	@Contract(pure = true)
	public WPIS_WriteBuffers(final WritePipeline aWritePipeline) {
		writePipeline = aWritePipeline;

		compilationPromise.then(_m_bus::setCompilation);

		// TODO see if clj has anything for csp
		_p_spsp.then((LSPrintStream.LSResult ls) -> {
			compilationPromise.then(c -> {
				final EOT_OutputFile outputFile = createOutputFile(ls, c, c.paths());
				_m_bus.addOutputFile(outputFile);
			});
		});
	}
	@NotNull
	private static EOT_OutputFile createOutputFile(final LSPrintStream.LSResult ls,
												   final @NotNull EDL_ICompilation c,
												   final @NotNull CP_Paths paths) {
		final CP_Path or    = paths.outputRoot();
		final File    file1 = or.subFile("buffers.txt").toFile(); // TODO subFile vs child

		final List<EIT_Input> fs;
		final EG_Statement    sequence;

		if (ls == null) {
			sequence = new EG_SingleStatement("<<>>");
			fs       =tripleo.elijah_fluffy.util.Helpers.List_of();
		} else {
			sequence = new EG_SequenceStatement(new EG_Naming("WriteBuffers"), ls.getStatement());
			fs       = ls.fs2(c);
		}

		// noinspection UnnecessaryLocalVariable
		final EOT_OutputFile off1 = new EOT_OutputFileImpl(fs, file1.toString(), EOT_OutputType.BUFFERS, sequence);
		return off1;
	}

	@Override
	public String sc_i_asString() {
		return "WPIS_WriteBuffers :: <interesting stuff here>";
	}

	//@Override
	public Operation<Ok> execute(final CK_Monitor aMonitor) {
		return null;
	}

	@Override
	public void act(final @NotNull WritePipelineSharedState st, final @NotNull WP_State_Control sc) {
		// 5. write buffers
		try {
			debug_buffers(st);
			sc.markSuccess(SC_Suc_.i(this));
		} catch (FileNotFoundException aE) {
			sc.markFailure(SC_Fai_.f(sc, aE));
		}
	}

	private void debug_buffers(final @NotNull WritePipelineSharedState st) throws FileNotFoundException {
		// TODO can/should this fail??

		st.c.spi(this);

		compilationPromise.resolve(st.c);

		//final List<Old_GenerateResultItem> generateResultItems1 = st.getGr().results();

		var or = st.c.paths().outputRoot();

		final CP_Path child = or.child("buffers.txt");

		child.getPathPromise().then((Path pp) -> {
			// final File file = pp.toFile();
			// final String s1 = file.toString();
			// Stupidity.println_err_3("8383 " + s1);

			// TODO nested promises is a latch
			writePipeline.getGenerateResultPromise().then((final @NotNull GenerateResult aGenerateResult) -> {
				final GenerateResult result1 = st.getGr();

				assert result1 == aGenerateResult;

				final LSPrintStream  sps     = new LSPrintStream();

				DebugBuffersLogic.debug_buffers_logic(result1, sps);
				V.gri(aGenerateResult);

				final LSPrintStream.LSResult _s = sps.getResult();

				_p_spsp.resolve(_s);
			});
		});
	}

	/*
	 * FIFO of changes(/effects?)
	 */
	static class Bus {
		private EDL_ICompilation c;

		public void addOutputFile(final @NotNull EOT_OutputFile off) {
			c.getOutputTree().add(off);
		}

		public void setCompilation(final @NotNull EDL_ICompilation cc) {
			c = cc;
		}
	}
}
