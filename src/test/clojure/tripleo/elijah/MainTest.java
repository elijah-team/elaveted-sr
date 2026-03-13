package tripleo.elijah;

import clojure.lang.*;
import org.junit.*;
import tripleo.elijah.comp.i.*;

public class MainTest {

	@Test
	public void main() {
	}

	@Test
	public void main3() {
		final var pl = new PersistentList(null)/*.empty()*/;
		final CompilerController    x     = Main.main3((PersistentList) pl, PersistentHashMap.EMPTY);
		Assert.assertNotNull(x);
	}
}
