package tripleo.elijah.lang.i;

import org.jetbrains.annotations.*;
import tripleo.elijah_elevated_durable.lang_model.*;

/**
 * @author Tripleo
 * <p>
 * Created Mar 23, 2020 at 12:40:27 AM
 */
public interface OS_NamedElement {
	EN_Name getEnName();

	/**
	 * The name of the element TODO Should this be a {@link IdentExpression}?
	 *
	 * @return a String
	 */
	@Contract(pure = true)
	String name();
}
