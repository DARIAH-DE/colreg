package eu.dariah.de.colreg.service;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import eu.dariah.de.colreg.dao.vocabulary.LanguageDao;
import eu.dariah.de.colreg.model.vocabulary.Language;

@Service
public class LanguageServiceImpl implements LanguageService {
	@Autowired private LanguageDao languageDao;
	
	@Override
	public List<Language> queryLanguages(String query) {
		
		Criteria[] queryCriteria = new Criteria[] {
				// Code match
				Criteria.where("code").regex(Pattern.compile("^" + query + '$', Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)),
				
				// Code starts with
				Criteria.where("code").regex(Pattern.compile("^" + query, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)),
				
				// Name starts with
				Criteria.where("name").regex(Pattern.compile("^" + query, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)),
				
				// Name likeness
				Criteria.where("name").regex(Pattern.compile(query, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE))
		};
		
		return languageDao.combineQueryResults(queryCriteria, 10);
	}

	@Override
	public Language findLanguageById(String id) {
		return languageDao.findById(id);
	}

	@Override
	public Language findLanguageByCode(String id) {
		return languageDao.findOne(Query.query(Criteria.where("code").is(id)));
	}
}
