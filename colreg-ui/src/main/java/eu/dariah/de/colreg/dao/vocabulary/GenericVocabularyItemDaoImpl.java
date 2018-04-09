package eu.dariah.de.colreg.dao.vocabulary;

import org.springframework.stereotype.Repository;

import eu.dariah.de.colreg.dao.base.BaseDaoImpl;
import eu.dariah.de.colreg.model.vocabulary.generic.VocabularyItem;

@Repository
public class GenericVocabularyItemDaoImpl extends BaseDaoImpl<VocabularyItem> implements GenericVocabularyItemDao {
	public GenericVocabularyItemDaoImpl() {
		super(VocabularyItem.class);
	}
}
