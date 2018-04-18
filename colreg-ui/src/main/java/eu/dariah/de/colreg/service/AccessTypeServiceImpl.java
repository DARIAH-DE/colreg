package eu.dariah.de.colreg.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import eu.dariah.de.colreg.dao.vocabulary.AccessTypeDao;
import eu.dariah.de.colreg.model.vocabulary.AccessType;

@Service
public class AccessTypeServiceImpl implements AccessTypeService {
	@Autowired private AccessTypeDao accessTypeDao;
	
	@Override
	public List<AccessType> findAllAccessTypes() {
		return accessTypeDao.findAll();
	}
	
	@Override
	public AccessType findAccessTypeById(String id) {
		return accessTypeDao.findById(id);
	}
	
	@Override
	public AccessType findAccessTypeByIdentfier(String identifier) {
		return accessTypeDao.findOne(Query.query(Criteria.where("identifier").is(identifier)));
	}
	
	@Override
	public Map<String, String> findAccessTypeIdLabelsMap() {
		Map<String, String> accessTypeIdLabelsMap = new HashMap<String, String>();
		for (AccessType t : this.findAllAccessTypes()) {
			accessTypeIdLabelsMap.put(t.getId(), t.getLabel());
		}
		return accessTypeIdLabelsMap;
	}
}
