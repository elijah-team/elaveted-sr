package tripleo.elijah;

import clojure.lang.*;
import org.jdeferred2.*;
import org.junit.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah_clojure.example.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.graph.*;

import java.io.*;

public class MainTest {

	@Test(expected = FileNotFoundException.class)
	public void main1() throws Exception {
		var x = CljExampleMain.callClojure("ns", "fn");
		// assertThat(x).isNotNull();
		Assert.assertNotNull(x);
	}

	@Test
	public void ctestalike() throws Exception {
		var x = CljExampleMain.callClojure("evaleted-lein.core-test", "hello");
		Assert.assertEquals("non-existent", x);
	}

	@Test
	public void main3() {
		final String             b_test = "test/demo-el-normal/main2";
		final var                pl     = new PersistentList(b_test);
		final CompilerController x      = Main.main3(pl, PersistentHashMap.EMPTY);
		Assert.assertNotNull(x);
		x.onConfig(new DoneCallback<IPersistentMap>() {
			@Override
			public void onDone(final IPersistentMap result) {
				NotImplementedException.raise_stop();
			}
		});
		final CompilationInterfaceRevised2 revised2 = x.revised2();
		assertNotNull(revised2);
		final CompilationInterfaceRevised revised = x.revised();
		assertNotNull(revised);
	}

	private <T> void assertNotNull(final T aRevised2) {
		Assert.assertNotNull(aRevised2);
	}
}
