package eu.dariah.de.colreg.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import eu.dariah.de.colreg.dao.vocabulary.generic.VocabularyItemDao;
import eu.dariah.de.colreg.model.vocabulary.generic.VocabularyItem;

@Service
public class VocabularyItemServiceImpl implements VocabularyItemService {
	@Autowired private VocabularyItemDao vocabularyItemDao;
	
	@Override
	public List<VocabularyItem> findVocabularyItems(String vocabularyIdentifier) {
		return vocabularyItemDao.find(Query.query(Criteria.where("vocabularyIdentifier").is(vocabularyIdentifier)));
	}

	@Override
	public VocabularyItem createVocabularyItem(String vocabularyIdentifier) {
		VocabularyItem vi = new VocabularyItem();
		vi.setId("new");
		vi.setVocabularyIdentifier(vocabularyIdentifier);
		return vi;
	}

	@Override
	public VocabularyItem findVocabularyItemById(String vocabularyIdentifier, String vocabularyItemId) {
		VocabularyItem vi = vocabularyItemDao.findById(vocabularyItemId);
		Assert.isTrue(vi.getVocabularyIdentifier().equals(vocabularyIdentifier));
		return vi;
	}

	@Override
	public List<VocabularyItem> findVocabularyItemByIdentifier(String vocabularyIdentifier, String identifier) {
		return vocabularyItemDao.find(Query.query(Criteria.where("vocabularyIdentifier").is(vocabularyIdentifier).and("identifier").is(identifier)));
	}

	@Override
	public void saveVocabularyItem(VocabularyItem vocabularyItem) {
		if (vocabularyItem.getId().equals("new")) {
			vocabularyItem.setId(null);
		}
		vocabularyItemDao.save(vocabularyItem);
	}
}
