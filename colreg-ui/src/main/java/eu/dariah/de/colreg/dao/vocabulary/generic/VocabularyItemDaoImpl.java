package eu.dariah.de.colreg.dao.vocabulary.generic;

import org.springframework.stereotype.Repository;

import eu.dariah.de.colreg.dao.base.BaseDaoImpl;
import eu.dariah.de.colreg.model.vocabulary.generic.VocabularyItem;

@Repository
public class VocabularyItemDaoImpl extends BaseDaoImpl<VocabularyItem> implements VocabularyItemDao {
	public VocabularyItemDaoImpl() {
		super(VocabularyItem.class);
	}
}
