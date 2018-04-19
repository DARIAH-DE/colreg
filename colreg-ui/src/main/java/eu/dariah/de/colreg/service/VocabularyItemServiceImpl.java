package eu.dariah.de.colreg.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import eu.dariah.de.colreg.dao.CollectionDao;
import eu.dariah.de.colreg.dao.vocabulary.generic.VocabularyItemDao;
import eu.dariah.de.colreg.model.Collection;
import eu.dariah.de.colreg.model.vocabulary.generic.VocabularyItem;

@Service
public class VocabularyItemServiceImpl implements VocabularyItemService {
	protected final static Logger logger = LoggerFactory.getLogger(VocabularyItemServiceImpl.class);
	
	@Autowired private VocabularyItemDao vocabularyItemDao;
	
	@Autowired private CollectionService collectionService;
	@Autowired private CollectionDao collectionDao;
	
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
	public VocabularyItem findVocabularyItemById(String vocabularyItemId) {
		VocabularyItem vi = vocabularyItemDao.findById(vocabularyItemId);
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
		} else {
			VocabularyItem currentItem = this.findVocabularyItemById(vocabularyItem.getId());
			if (currentItem!=null && !currentItem.getIdentifier().equals(vocabularyItem.getIdentifier())) {
				List<Collection> updateCollections = collectionDao.find(Query.query(Criteria.where(currentItem.getVocabularyIdentifier()).is(currentItem.getIdentifier())));
				PropertyAccessor collectionAccessor;
				for (Collection c : updateCollections) {
					collectionAccessor = PropertyAccessorFactory.forDirectFieldAccess(c);
					
					@SuppressWarnings("unchecked")
					List<String> items = (List<String>)collectionAccessor.getPropertyValue(currentItem.getVocabularyIdentifier());
					
					items.remove(currentItem.getIdentifier());
					items.add(vocabularyItem.getIdentifier());
					
					collectionAccessor.setPropertyValue(currentItem.getVocabularyIdentifier(), items);
					
					// CollectionDao.save() -> silent save (no semantic change)
					collectionDao.save(c);
				}
			}
		}
		vocabularyItemDao.save(vocabularyItem);
	}
	
	@Override
	public List<Collection> findCurrentMatchingCollections(String vocabularyIdentifier, String vocabularyItemIdentifier, String userId) {
		return collectionDao.find(Query.query(Criteria
				.where("succeedingVersionId").exists(false)
				.orOperator(Criteria.where("draftUserId").exists(false), Criteria.where("draftUserId").is(userId))
				.and(vocabularyIdentifier).is(vocabularyItemIdentifier)));
	}
	
	@Override
	public int deleteVocabularyItem(VocabularyItem vi, String userId) {
		int matchingCollectionVersions = collectionDao.find(Query.query(Criteria.where(vi.getVocabularyIdentifier()).is(vi.getIdentifier()))).size();
		
		// No collection versions matching -> really delete vocabulary item
		if (matchingCollectionVersions==0) {
			vocabularyItemDao.delete(vi);
			return 0;
		}
		
		// Otherwise create new versions of affected collections without vocabulary item and set deleted
		vi.setDeleted(true);
		vocabularyItemDao.save(vi);
		
		List<Collection> cs = this.findCurrentMatchingCollections(vi.getVocabularyIdentifier(), vi.getIdentifier(), userId);
		int count = 0;
		
		PropertyAccessor collectionAccessor;
		for (Collection c : cs) {
			try {
				collectionAccessor = PropertyAccessorFactory.forDirectFieldAccess(c);
				
				@SuppressWarnings("unchecked")
				List<String> items = (List<String>)collectionAccessor.getPropertyValue(vi.getVocabularyIdentifier());
				items.remove(vi.getIdentifier());
				
				collectionAccessor.setPropertyValue(vi.getVocabularyIdentifier(), items);
				
				// CollectionService.save() -> new version
				collectionService.save(c, userId);
				count++;
			} catch (Exception e) {
				logger.error(String.format("Failed to batch update vocabulary item [%s] on collection [%s]", vi.getIdentifier(), c.getEntityId()), e);
			}
		}
		
		// If only current versions contained the item, we might be able to delete the item now
		matchingCollectionVersions = collectionDao.find(Query.query(Criteria.where(vi.getVocabularyIdentifier()).is(vi.getIdentifier()))).size();
		if (matchingCollectionVersions==0) {
			vocabularyItemDao.delete(vi);
			return 0;
		}
		
		return count;
	}
}
