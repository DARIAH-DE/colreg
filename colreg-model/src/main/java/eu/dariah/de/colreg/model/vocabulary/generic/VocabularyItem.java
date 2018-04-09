package eu.dariah.de.colreg.model.vocabulary.generic;

import de.unibamberg.minf.dme.model.base.BaseIdentifiable;

public class VocabularyItem extends BaseIdentifiable {
	private static final long serialVersionUID = 942372659078068387L;
	
	private String vocabularyId;
	private String identifier;
	private String name;
	
	
	public String getVocabularyId() { return vocabularyId; }
	public void setVocabularyId(String vocabularyId) { this.vocabularyId = vocabularyId; }
	
	public String getIdentifier() { return identifier; }
	public void setIdentifier(String identifier) { this.identifier = identifier; }
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
}