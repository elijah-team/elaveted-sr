/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_fluffy.util;

@SuppressWarnings("unused")
public class NotImplementedException extends RuntimeException {
	public NotImplementedException() {
		int y=2;
	}

	public NotImplementedException(String message) {
		super(message);
		int y=2;
	}

    public static void raise() {
		int y=2;
	}

	public static void raise_stop() {
		int y=2;
	}
}
