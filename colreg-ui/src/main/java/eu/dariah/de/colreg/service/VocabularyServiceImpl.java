package eu.dariah.de.colreg.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import eu.dariah.de.colreg.dao.vocabulary.generic.VocabularyDao;
import eu.dariah.de.colreg.model.vocabulary.generic.Vocabulary;

@Service
public class VocabularyServiceImpl implements VocabularyService {
	@Autowired private VocabularyDao vocabularyDao;
	
	@Override
	public List<Vocabulary> findVocabularies() {
		return vocabularyDao.findAll();
	}

	@Override
	public Vocabulary findVocabulary(String vocabularyId) {
		return vocabularyDao.findOne(Query.query((new Criteria()).orOperator(Criteria.where("id").is(vocabularyId), Criteria.where("identifier").is(vocabularyId))));
	}

	@Override
	public Vocabulary findVocabularyByIdentifier(String identifier) {
		return vocabularyDao.findOne(Query.query(Criteria.where("identifier").is(identifier)));
	}
}
