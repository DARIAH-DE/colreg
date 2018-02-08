package eu.dariah.de.colreg.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import eu.dariah.de.colreg.dao.AgentDao;
import eu.dariah.de.colreg.dao.CollectionDao;
import eu.dariah.de.colreg.dao.vocabulary.AccessTypeDao;
import eu.dariah.de.colreg.dao.vocabulary.AccrualMethodDao;
import eu.dariah.de.colreg.dao.vocabulary.AccrualPeriodicityDao;
import eu.dariah.de.colreg.dao.vocabulary.AccrualPolicyDao;
import eu.dariah.de.colreg.model.Access;
import eu.dariah.de.colreg.model.Accrual;
import eu.dariah.de.colreg.model.Collection;
import eu.dariah.de.colreg.model.CollectionAgentRelation;
import eu.dariah.de.colreg.model.LocalizedDescription;
import eu.dariah.de.colreg.model.vocabulary.AccrualMethod;
import eu.dariah.de.colreg.model.vocabulary.AccrualPeriodicity;
import eu.dariah.de.colreg.model.vocabulary.AccrualPolicy;
import eu.dariah.de.colreg.pojo.AccessPojo;
import eu.dariah.de.colreg.pojo.AccrualPojo;
import eu.dariah.de.colreg.pojo.CollectionPojo;
import eu.dariah.de.colreg.pojo.DcddmCollectionPojo;
import eu.dariah.de.colreg.service.ImageServiceImpl.ImageTypes;
import eu.dariah.de.dariahsp.model.web.AuthPojo;

@Service
public class CollectionServiceImpl implements CollectionService {
	@Autowired private CollectionDao collectionDao;
	@Autowired private AgentDao agentDao;
	@Autowired private AccessTypeDao accessTypeDao;
	
	@Autowired private AccrualMethodDao accMethodDao;
	@Autowired private AccrualPeriodicityDao accPeriodicityDao;
	@Autowired private AccrualPolicyDao accPolicyDao;

	@Autowired private ImageService imageService;
	
	@Override
	public Collection createCollection(String userId) {
		Collection c = new Collection();
		c.setId("new");
		c.setEntityId("new");
		c.setDraftUserId(userId);
		
		return c;
	}

	@Override
	public void save(Collection c, String userId) {
		Collection prev = this.findCurrentByCollectionId(c.getEntityId());
		
		c.setId(null);
		if (c.getEntityId()==null || c.getEntityId().isEmpty() || c.getEntityId().equals("new")) {
			c.setEntityId(new ObjectId().toString());
		}
		c.setSucceedingVersionId(null);
		c.setVersionCreator(userId);
		c.setVersionTimestamp(DateTime.now());
		
		if (prev!=null) {
			c.setEntityCreator(prev.getEntityCreator());
			c.setEntityTimestamp(prev.getEntityTimestamp());
		} else {
			c.setEntityCreator(c.getVersionCreator());
			c.setEntityTimestamp(c.getVersionTimestamp());
		}
		
		if (c.getCollectionImage()!=null && c.getCollectionImage().isEmpty()) {
			c.setCollectionImage(null);
		}
		
		// Save new object first...just to be sure
		collectionDao.save(c);
		
		if (prev!=null) {
			prev.setSucceedingVersionId(c.getId());
			collectionDao.save(prev);
		}		
	}

	@Override
	public Collection findCurrentByCollectionId(String id) {
		return collectionDao.findCurrentById(id);
	}

	@Override
	public List<Collection> findAllCurrent() {
		return collectionDao.findAllCurrent();
	}
	
	@Override
	public List<Collection> findAllDrafts(String userId) {
		return collectionDao.findAllDrafts(userId);
	}

	@Override
	public void initializeAgentRelations(Collection c) {
		if (c.getAgentRelations()!=null) {
			for (CollectionAgentRelation ar : c.getAgentRelations()) {
				if (ar.getAgentId()!=null) {
					ar.setAgent(agentDao.findCurrentById(ar.getAgentId()));
				}
			}
		}
	}

	@Override
	public List<Collection> queryCollections(String query, List<String> excl) {
		Criteria cBase = Criteria.where("succeedingVersionId").is(null);
		if (excl!=null) {
			if (excl.size()>1) {
				cBase.and("entityId").nin(excl);
			} else {
				cBase.and("entityId").ne(excl.get(0));
			}
		}
		
		cBase.orOperator(Criteria.where("draftUserId").exists(false), Criteria.where("draftUserId").is(""));

		// TODO: Include foreName for query
		Criteria[] queryCriteria = new Criteria[] {
				// ID match
				Criteria.where("id").is(query).andOperator(cBase),
				

				// Title starts with
				Criteria.where("localizedDescriptions").elemMatch(
						Criteria.where("title").regex(Pattern.compile("^" + query, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE))
				).andOperator(cBase),
				
				// Title likeness
				Criteria.where("localizedDescriptions").elemMatch(
						Criteria.where("title").regex(Pattern.compile(query, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE))
				).andOperator(cBase)
		};
		return collectionDao.combineQueryResults(queryCriteria, 10);
	}

	@Override
	public List<Collection> findCurrentByParentCollectionId(String id) {
		return collectionDao.findCurrentByParentCollectionId(id);
	}

	@Override
	public List<Collection> findCurrentByRelatedAgentId(String id) {
		Criteria c = Criteria.where("succeedingVersionId").is(null)
				.and("agentRelations").elemMatch(Criteria.where("agentId").is(id));
		c.orOperator(Criteria.where("draftUserId").exists(false), Criteria.where("draftUserId").is(""));
		
		Query q = new Query();
		q.addCriteria(c);		

		return collectionDao.find(q);
	}

	@Override
	public Collection findCurrentByCollectionId(String id, boolean includeDeleted) {
		return collectionDao.findCurrentById(id, includeDeleted);
	}

	@Override
	public List<Collection> findAllVersionsForEntityId(String id) {
		Query q = new Query();
		q.addCriteria(Criteria.where("entityId").is(id));
		q.with(new Sort(Sort.Direction.DESC, "versionTimestamp"));
		q.fields().include("id")
			.include("succeedingVersionId")
			.include("entityId")
			.include("versionTimestamp")
			.include("versionCreator")
			.include("versionComment")
			.include("deleted")
			.include("draftUserId");
		
		return collectionDao.find(q);
	}

	@Override
	public Collection findVersionById(String id, boolean includeDeleted) {
		return collectionDao.findById(id, includeDeleted);
	}
	
	@Override
	public void appendVersionComment(String versionid, String comment) {
		Collection c = collectionDao.findById(versionid, true);
		c.setVersionComment(comment);
		collectionDao.save(c);
	}

	@Override
	public List<CollectionPojo> convertToPojos(List<Collection> collections, Locale locale) {
		return this.convertToPojos(CollectionPojo.class, collections, locale);
	}
	
	@Override
	public <T extends CollectionPojo> List<T> convertToPojos(Class<T> clazz, List<Collection> collections, Locale locale) {
		if (collections==null) {
			return null;
		}
		List<T> pojos = new ArrayList<T>(collections.size());
		for (Collection c : collections) {
			pojos.add(this.convertToPojo(clazz, c, locale));
		}
		return pojos;
	}

	@Override
	public <T extends CollectionPojo> T convertToPojo(Class<T> clazz, Collection collection, Locale locale) {
		if (collection==null) {
			return null;
		}
		
		if (!DcddmCollectionPojo.class.isAssignableFrom(clazz)) {
			return clazz.cast(this.fillCollectionPojo(new CollectionPojo(), collection, locale));
		}
		DcddmCollectionPojo pojo = this.fillCollectionPojo(new DcddmCollectionPojo(), collection, locale);
		
		pojo.setAccessPojos(convertAccessToPojos(collection.getAccessMethods()));
		pojo.setAccrualPojos(convertAccrualToPojos(collection.getAccrualMethods()));
		pojo.setImageUrl(imageService.getImageURI(collection.getCollectionImage(), null));
		
		return clazz.cast(pojo);
	}
	
	@Override
	public List<Collection> findLatestChanges(int i, AuthPojo auth) {
		Criteria c = new Criteria();
		c.orOperator(Criteria.where("draftUserId").exists(false), Criteria.where("draftUserId").is(auth.getUserId()));
		
		Query q = new Query(c);
		q.limit(i);
		q.with(new Sort(Sort.Direction.DESC, "versionTimestamp"));
		
		return collectionDao.find(q);
	}
	

	private <T extends CollectionPojo> T fillCollectionPojo(T pojo, Collection collection, Locale locale) {
		//CollectionPojo pojo = new CollectionPojo();
		pojo.setVersionId(collection.getId());
		pojo.setVersionTimestamp(collection.getVersionTimestamp().toInstant().getMillis());
		pojo.setEntityId(collection.getEntityId());
		pojo.setParentEntityId(collection.getParentCollectionId());
		pojo.setId(collection.getId());
		
		if (locale!=null) {
			pojo.setLastChanged(
					"<span style=\"white-space: nowrap;\">" + 
							collection.getVersionTimestamp().toString(DateTimeFormat.patternForStyle("L-", locale), locale) +
					"</span> <span style=\"white-space: nowrap;\">" + 
							collection.getVersionTimestamp().toString(DateTimeFormat.patternForStyle("-M", locale), locale) +
					"</span>");
			
			// TODO: Actually use the one we need not the first
			pojo.setTitle(collection.getLocalizedDescriptions().get(0).getTitle());
			if (collection.getLocalizedDescriptions().get(0).getAcronym()!=null && 
					!collection.getLocalizedDescriptions().get(0).getAcronym().trim().isEmpty()) {
				pojo.setTitle(pojo.getTitle() + " (" + collection.getLocalizedDescriptions().get(0).getAcronym() + ")");
			}
		} else {
			Map<String, String> titles = new HashMap<String, String>();
			for (LocalizedDescription desc : collection.getLocalizedDescriptions()) {
				titles.put(desc.getLanguageId(), desc.getTitle());
			}
			pojo.setTitles(titles);
		}		
			
		if (collection.getAccessMethods()!=null && collection.getAccessMethods().size()>0) {
			String accessTypes = "";
			for (int i=0; i<collection.getAccessMethods().size(); i++) {
				accessTypes += accessTypeDao.findById(collection.getAccessMethods().get(i).getType()).getLabel();
				if (i<collection.getAccessMethods().size()-1) {
					accessTypes += "; ";
				}
			}
			pojo.setAccess(accessTypes);
			
		}
		
		pojo.setThumbnailUrl(imageService.getImageURI(collection.getCollectionImage(), ImageTypes.THUMBNAIL));
		
		pojo.setType(collection.getCollectionType());
		pojo.setState(collection.isDeleted() ? "deleted" : collection.getDraftUserId()==null||collection.getDraftUserId().isEmpty() ? "published" : "draft");
		return pojo;
	}
	
	private List<AccessPojo> convertAccessToPojos (List<Access> as) {
		List<AccessPojo> aPojos = null;
		if (as!=null) {
			aPojos = new ArrayList<AccessPojo>();
			for (Access a : as) {
				aPojos.add(convertAccessToPojo(a));
			}
		}
		return aPojos;
	}
	
	private List<AccrualPojo> convertAccrualToPojos(List<Accrual> accrualMethods) {
		if (accrualMethods==null || accrualMethods.size()==0) {
			return null;
		}
		List<AccrualPojo> accrualPojos = new ArrayList<AccrualPojo>();
		AccrualPojo accPojo;
		AccrualMethod accMethod;
		AccrualPolicy accPolicy;
		AccrualPeriodicity accPeriodicity;
		
		for (Accrual acc : accrualMethods) {
			accPojo = new AccrualPojo();
			
			accMethod = accMethodDao.findById(acc.getAccrualMethod());
			if (accMethod!=null) {
				accPojo.setAccrualMethod(accMethod.getIdentifier());
			}
			accPolicy = accPolicyDao.findById(acc.getAccrualPolicy());
			if (accPolicy!=null) {
				accPojo.setAccrualPolicy(accPolicy.getIdentifier());
			}
			accPeriodicity = accPeriodicityDao.findById(acc.getAccrualPeriodicity());
			if (accPeriodicity!=null) {
				accPojo.setAccrualPeriodicity(accPeriodicity.getIdentifier());	
			}
			accrualPojos.add(accPojo);
		}
		
		return accrualPojos;
	}
	
	private AccessPojo convertAccessToPojo (Access a) {
		AccessPojo aPojo = null;
		if (a!=null) {
			aPojo = new AccessPojo();
			if (a.getSchemeIds()!=null) {
				aPojo.setSchemeIds(new ArrayList<String>(a.getSchemeIds()));
			}
			aPojo.setSet(a.getOaiSet());
			aPojo.setType(accessTypeDao.findById(a.getType()).getLabel());
			aPojo.setUri(a.getUri());
		}
		return aPojo;
	}

	@Override
	public long countCollections() {
		return collectionDao.count();
	}
	
	@Override
	public long countDrafts(String userId) {
		return collectionDao.countDrafts(userId);
	}
}
