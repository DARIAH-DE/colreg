package eu.dariah.de.colreg.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import eu.dariah.de.colreg.dao.LanguageDao;
import eu.dariah.de.colreg.model.Language;

@Service
public class LanguageServiceImpl implements LanguageService {
	@Autowired private LanguageDao languageDao;

	@Override
	public List<Language> queryLanguages(String query) {
		Query q;
		List<Language> result = new ArrayList<Language>();
		List<Language> innerResult;
		
		int maxTotalResults = 15;
		
		Criteria[] queryCriteria = new Criteria[] {
				// Code match
				Criteria.where("code").regex(Pattern.compile("^" + query + '$', Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)),
				
				// Name starts with
				Criteria.where("name").regex(Pattern.compile("^" + query, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)),
				
				// Name likeness
				Criteria.where("name").regex(Pattern.compile(query, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE))
		};
		
		for (Criteria c : queryCriteria) {
			q = new Query();
			q.addCriteria(c);
			q.limit(result.size() + maxTotalResults); // Could overlap
			innerResult = languageDao.find(q);
			if (innerResult!=null && innerResult.size()>0) {
				for (Language l : innerResult) {
					if (!result.contains(l)) {
						result.add(l);
					}
				}
				if (result.size()>=maxTotalResults) {
					return result.subList(0, maxTotalResults-1);
				}
			}
			
		}
		return result;
	}
}
