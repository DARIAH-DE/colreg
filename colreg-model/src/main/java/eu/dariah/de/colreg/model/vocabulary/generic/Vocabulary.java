package eu.dariah.de.colreg.model.vocabulary.generic;

import eu.dariah.de.colreg.model.base.LocalizableEntity;

public class Vocabulary extends LocalizableEntity {
	private static final long serialVersionUID = -6245008051654334916L;
	
	public static final String MESSAGE_CODE_PREFIX = "~eu.dariah.de.colreg.vocabularies.";

	@Override
	public String getMessageCodePrefix() {
		return MESSAGE_CODE_PREFIX;
	}
}