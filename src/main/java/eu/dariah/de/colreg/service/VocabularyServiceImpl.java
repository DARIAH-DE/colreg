package eu.dariah.de.colreg.service;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import eu.dariah.de.colreg.dao.vocabulary.AccessTypeDao;
import eu.dariah.de.colreg.dao.vocabulary.AccrualMethodDao;
import eu.dariah.de.colreg.dao.vocabulary.AccrualPolicyDao;
import eu.dariah.de.colreg.dao.vocabulary.AgentRelationTypeDao;
import eu.dariah.de.colreg.dao.vocabulary.AgentTypeDao;
import eu.dariah.de.colreg.dao.vocabulary.EncodingSchemeDao;
import eu.dariah.de.colreg.dao.vocabulary.LanguageDao;
import eu.dariah.de.colreg.model.vocabulary.AccessType;
import eu.dariah.de.colreg.model.vocabulary.AccrualMethod;
import eu.dariah.de.colreg.model.vocabulary.AccrualPolicy;
import eu.dariah.de.colreg.model.vocabulary.AgentRelationType;
import eu.dariah.de.colreg.model.vocabulary.AgentType;
import eu.dariah.de.colreg.model.vocabulary.EncodingScheme;
import eu.dariah.de.colreg.model.vocabulary.Language;

@Service
public class VocabularyServiceImpl implements VocabularyService {
	@Autowired private LanguageDao languageDao;
	
	@Autowired private AccessTypeDao accessTypeDao;
	@Autowired private AccrualMethodDao accrualMethodDao;
	@Autowired private AccrualPolicyDao accrualPolicyDao;
	@Autowired private AgentTypeDao agentTypeDao;
	@Autowired private AgentRelationTypeDao agentRelationTypeDao;
	@Autowired private EncodingSchemeDao encodingSchemeDao;
	
	@Override
	public List<Language> queryLanguages(String query) {
		
		Criteria[] queryCriteria = new Criteria[] {
				// Code match
				Criteria.where("code").regex(Pattern.compile("^" + query + '$', Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)),
				
				// Name starts with
				Criteria.where("name").regex(Pattern.compile("^" + query, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)),
				
				// Name likeness
				Criteria.where("name").regex(Pattern.compile(query, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE))
		};
		
		return languageDao.combineQueryResults(queryCriteria, 10);
	}
	
	@Override
	public List<EncodingScheme> queryEncodingSchemes(String query) {		
		Criteria[] queryCriteria = new Criteria[] {
				// Name match
				Criteria.where("name").regex(Pattern.compile("^" + query + '$', Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)),
				
				// Name starts with
				Criteria.where("name").regex(Pattern.compile("^" + query, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)),
				
				// Name likeness
				Criteria.where("name").regex(Pattern.compile(query, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE))
		};
		return encodingSchemeDao.combineQueryResults(queryCriteria, 10);
	}

	@Override
	public List<AccrualMethod> findAllAccrualMethods() {
		return accrualMethodDao.findAll();
	}

	@Override
	public List<AccrualPolicy> findAllAccrualPolicies() {
		return accrualPolicyDao.findAll();
	}

	@Override
	public List<AccessType> findAllAccessTypes() {
		return accessTypeDao.findAll();
	}
	
	@Override
	public List<AgentType> findAllAgentTypes() {
		return agentTypeDao.findAll();
	}
	
	@Override
	public List<EncodingScheme> findAllEncodingSchemes() {
		return encodingSchemeDao.findAll();
	}
	
	@Override
	public List<AgentRelationType> findAllAgentRelationTypes() {
		return agentRelationTypeDao.findAll();
	}

	@Override
	public Language findLanguageById(String id) {
		return languageDao.findById(id);
	}

	@Override
	public Language findLanguageByCode(String id) {
		return languageDao.findOne(Query.query(Criteria.where("code").is(id)));
	}

	@Override
	public EncodingScheme findEncodingSchemeById(String id) {
		return encodingSchemeDao.findById(id);
	}

	@Override
	public EncodingScheme findEncodingSchemeByName(String id) {
		return encodingSchemeDao.findOne(Query.query(Criteria.where("name").is(id)));
	}
}
