package eu.dariah.de.colreg.dao.vocabulary;

import org.springframework.stereotype.Repository;

import eu.dariah.de.colreg.dao.base.BaseDaoImpl;
import eu.dariah.de.colreg.model.vocabulary.GenericVocabularyItem;

@Repository
public class GenericVocabularyItemDaoImpl extends BaseDaoImpl<GenericVocabularyItem> implements GenericVocabularyItemDao {
	public GenericVocabularyItemDaoImpl() {
		super(GenericVocabularyItem.class);
	}
}
