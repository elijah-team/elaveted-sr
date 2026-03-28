package tripleo.elijah;

import clojure.lang.*;
import org.junit.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah_clojure.example.*;

public class MainTest {

	@Test
	public void main1() throws Exception {
		var x= CljExampleMain.callClojure("ns", "fn");
		//assertThat(x).isNotNull();
		Assert.assertNotNull(x);
	}

	@Test
	public void main3() {
		final String             b_test = "test/demo-el-normal/main2";
		final var                pl     = new PersistentList(b_test);
		final CompilerController x      = Main.main3(pl, PersistentHashMap.EMPTY);
		Assert.assertNotNull(x);
	}
}
