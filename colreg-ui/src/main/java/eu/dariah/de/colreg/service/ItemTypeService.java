package eu.dariah.de.colreg.service;

import java.util.List;

import eu.dariah.de.colreg.model.vocabulary.ItemType;

public interface ItemTypeService {
	public List<ItemType> findAllItemTypes();
	public ItemType findItemTypeById(String id);
	public ItemType findItemTypeByIdentifier(String id);
}
