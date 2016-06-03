package eu.dariah.de.colreg.dao.vocabulary;

import org.springframework.stereotype.Repository;

import eu.dariah.de.colreg.dao.base.BaseDaoImpl;
import eu.dariah.de.colreg.model.vocabulary.ItemType;

@Repository
public class ItemTypeDaoImpl extends BaseDaoImpl<ItemType> implements ItemTypeDao {
	public ItemTypeDaoImpl() {
		super(ItemType.class);
	}
}
