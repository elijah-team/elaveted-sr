/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_fluffy.util;

import antlr.*;
import org.apache.commons.codec.digest.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.comp.i.*;

import java.io.*;
import java.security.*;
import java.util.*;

import static org.apache.commons.codec.digest.MessageDigestAlgorithms.*;

/**
 * Created 9/10/20 3:44 PM
 */
public class Helpers {
/*
	public static void printXML(final Object obj, @NotNull final TabbedOutputStream tos) {
		SimplePrintLoggerToRemoveSoon.println_err2("** XStream support has been disabled");
		final XStream x = new XStream();
		//x.setMode(XStream.ID_REFERENCES);
		x.toXML(obj, tos.getStream());
	}
*/

	@NotNull
	public static <E> List<E> List_of(@NotNull final E... e1) {
		final List<E> r = new ArrayList<E>();
		Collections.addAll(r, e1);
		return r;
	}

	public static Token makeToken(final String aText) {
		final CommonToken t = new CommonToken();
		t.setText(aText);
		return t;
	}

	@NotNull
	public static String remove_single_quotes_from_string(final @NotNull String s) {
		assert s.length() >= 3;
		return s.substring(1, s.length() - 1);
	}

	public static String String_join(final String separator, final Iterable<String> stringIterable) {
		if (false) {
			final StringBuilder sb = new StringBuilder();

			for (final String part : stringIterable) {
				sb.append(part);
				sb.append(separator);
			}
			final String          ss        = sb.toString();
			final @NotNull String substring = separator.substring(0, ss.length() - separator.length());
			return substring;
		}
		// since Java 1.8
		return String.join(separator, stringIterable);
	}

	public static Operation<@NotNull String> getHashForFilename(final String aFilename) {
		Operation<String> result;
		try {
			final String digest = new DigestUtils(SHA_256).digestAsHex(new File(aFilename));
			result = Operation.success(digest);
		} catch (IOException aE) {
			result = Operation.failure(aE);
		}
		return result;
	}

/*
	@Nullable
	public static String getHashForFilenameJava(final @NotNull String aFilename, final ErrSink aErrSink) throws IOException {
		final File      file = new File(aFilename);
		final long      size = file.length();
		final byte[]    ba   = new byte[(int) size];  // README Counting on reasonable sizes here
		FileInputStream bb   = null;
		try {
			bb = new FileInputStream(file);
			bb.read(ba);

			try {
				final String hh = getHash(ba);
				return hh;
			} catch (final NoSuchAlgorithmException aE) {
				aErrSink.exception(aE);
//				aE.printStackTrace();
			}
		} finally {
			if (bb != null)
				bb.close();
		}
		return null;
	}
*/

	public static String getHash(final byte[] aBytes) throws NoSuchAlgorithmException {
		final MessageDigest md = MessageDigest.getInstance("SHA-256");

//		String input;
//		md.update(input.getBytes(StandardCharsets.UTF_8));
		md.update(aBytes);

		final byte[] hashBytes = md.digest();

		final StringBuilder sb = new StringBuilder();
		for (final byte b : hashBytes) {
			sb.append(String.format("%02x", b));
		}

		return sb.toString();
	}

	public static boolean String_equals(final String aLeft, final String aRight) {
		return Objects.equals(aLeft, aRight);
	}
}

//
//
//
