/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.ci_impl;


import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah_durable_elevated.ci_impl.*;
import tripleo.elijah_durable_elevated.nextgen.inputtree.*;
import tripleo.elijah_fluffy.util.*;
import tripleo.elijah_fluffy.xlang.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created 9/6/20 11:20 AM
 */
public class CompilerInstructionsImpl implements CompilerInstructions {
	public @NotNull List<LibraryStatementPart> lsps = new ArrayList<>();
	private         CiIndexingStatement        _idx;
	private         String                     filename;
	private         GenerateStatement          gen;
	private         String                     name;
	private         CompilerInput              advised;

	@Override
	public void add(final GenerateStatement generateStatement) {
		assert gen == null;
		gen = generateStatement;
	}

	@Override
	public void add(final @NotNull LibraryStatementPart libraryStatementPart) {
		libraryStatementPart.setInstructions(this);
		lsps.add(libraryStatementPart);
	}

	@Override
	public List<LibraryStatementPart> getLibraryStatementParts() {
		return lsps;
	}

	@Override
	public @Nullable Optional<String> genLang() {
		//noinspection UnnecessaryLocalVariable
		final Optional<String> genLang = ((GenerateStatementImpl) gen).dirs.stream()
				.filter(input -> input.getName().equals("gen"))
				.findAny() // README if you need more than one, comment this out
				.stream().map((GenerateStatementImpl.Directive gin) -> {
					final CiExpression lang_raw = gin.getExpression();
					if (lang_raw instanceof final CiStringExpression langRaw) {
						final String s = CiHelpers.remove_single_quotes_from_string(langRaw.getText());
						return Optional.of(s);
					} else {
						return Optional.<String>empty();
					}
				})
				.findFirst() // README here too
				.orElse(null);

		return genLang;
	}

	@Override
	public String getName() {
		// back and forth
		return this.name;
	}

	@Override
	public void setName(final LocatableString name) {
		this.name = name.asLocatableString();
	}

	@Override
	public LocatableString getLocatableName() {
		assert name != null;
		return LocatableString.of(name);
	}

	@Override
	public @NotNull CiIndexingStatement indexingStatement() {
		if (_idx == null)
			_idx = new CiIndexingStatementImpl(this);

		return _idx;
	}

	@Override
	public List<LibraryStatementPart> _lsps() {
		return null;
	}

	@Override
	public void advise(final CompilerInput aCompilerInput) {
		advised = aCompilerInput;
	}

	@Override
	public String getFilename() {
		return filename;
	}

	@Override
	public void setFilename(final String filename) {
		this.filename = filename;
	}

	//@Override
	public tripleo.wrap.File makeFile() {
		//if (advised != null) {
		//	return new File(advised.makeFile(), getFilename());
		//} else {
		return tripleo.wrap.File.wrap(new File(getFilename()));
		//}
	}

	@Override
	public void addUnderstandingListener(final Class<EIT_ModuleInputImpl.LangOfModule> aLangOfModuleClass, final EIT_ModuleInputImpl.UnderstandingListener aUnderstandingListener) {
		throw new UnintendedUseException("implement me");
	}

	@Override
	public String toString() {
		return "CompilerInstructionsImpl{name='%s', filename='%s'}".formatted(name, filename);
	}
}

//
//
//
