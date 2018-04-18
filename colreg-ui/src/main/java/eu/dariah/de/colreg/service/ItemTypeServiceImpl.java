package eu.dariah.de.colreg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import eu.dariah.de.colreg.dao.vocabulary.ItemTypeDao;
import eu.dariah.de.colreg.model.vocabulary.ItemType;

@Service
public class ItemTypeServiceImpl implements ItemTypeService {
	@Autowired private ItemTypeDao itemTypeDao;
	
	@Override
	public List<ItemType> findAllItemTypes() {
		return itemTypeDao.findAll();
	}

	@Override
	public ItemType findItemTypeById(String id) {
		return itemTypeDao.findById(id);
	}

	@Override
	public ItemType findItemTypeByIdentifier(String id) {
		return itemTypeDao.findOne(Query.query(Criteria.where("identifier").is(id)));
	}
}
