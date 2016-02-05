package eu.dariah.de.colreg.model.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import eu.dariah.de.colreg.model.Collection;
import eu.dariah.de.colreg.model.LocalizedDescription;
import eu.dariah.de.colreg.service.AgentService;
import eu.dariah.de.colreg.service.CollectionService;
import eu.dariah.de.colreg.service.VocabularyService;

@Component
public class CollectionValidator extends BaseValidator<Collection> {	
	@Autowired private CollectionService collectionService;
	@Autowired private AgentService agentService;
	@Autowired private VocabularyService vocabularyService;
	
	public CollectionValidator() {
		super(Collection.class);
	}

	@Override
	public void preprocess(Collection collection) {
		Collection cCurrent = collectionService.findCurrentByCollectionId(collection.getEntityId());
		if (cCurrent==null) {
			// TODO: Actual user ids
			collection.setDraftUserId("system_user_id");
		} else {
			// Stays with its draft creator or published
			collection.setDraftUserId(cCurrent.getDraftUserId());
		}
		
		if (collection.getLocalizedDescriptions()!=null && collection.getLocalizedDescriptions().size()>0) {
			List<LocalizedDescription> emptyDescriptions = new ArrayList<LocalizedDescription>();
			for (LocalizedDescription desc : collection.getLocalizedDescriptions()) {
				if (desc.isEmpty()) {
					emptyDescriptions.add(desc);
				}
			}
			collection.getLocalizedDescriptions().removeAll(emptyDescriptions);
		}
		
	}

	@Override
	public void validate(Collection collection, Errors errors) {
		// Check languages ok
		// Check agent id ok
		// Check parent collection id
		
		if (collection.getItemLanguages()!=null && collection.getItemLanguages().size()>0) {
			boolean itemLanguageError = false;
			for (int i=0; i<collection.getItemLanguages().size(); i++) {
				if (vocabularyService.findLanguageByCode(collection.getItemLanguages().get(i))==null) {
					errors.rejectValue("itemLanguages[" + i + "]", "~not_a_valid_language", "~not_a_valid_language");
					itemLanguageError = true;
				}
			}
			
			if (itemLanguageError) {
				errors.rejectValue("itemLanguages", "~at_least_one_is_messed_up", "~at_least_one_is_messed_up");
			}
		}
	}
}
