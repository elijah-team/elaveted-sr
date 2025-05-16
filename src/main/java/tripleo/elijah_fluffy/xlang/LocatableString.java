package tripleo.elijah_fluffy.xlang;

import antlr.*;

import java.util.*;

public interface LocatableString {
	static LocatableString of(Token aToken) {
		 return new LocatableString() {
			 @Override public String asLocatableString() {
				return aToken.getText();
			}
		};
	}

	static LocatableString of(String aAbsolutePath) {
		return new LocatableString() {
			@Override
			public String asLocatableString() {
				return aAbsolutePath;
			}
		};
	}

	String asLocatableString();

	default boolean sameString(String aName) {
		return Objects.equals(aName, asLocatableString());
	}
}
