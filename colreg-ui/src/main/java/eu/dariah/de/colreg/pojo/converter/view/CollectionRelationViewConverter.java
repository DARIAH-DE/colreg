package eu.dariah.de.colreg.pojo.converter.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Component;

import eu.dariah.de.colreg.model.CollectionRelation;
import eu.dariah.de.colreg.pojo.converter.base.BaseConverter;
import eu.dariah.de.colreg.pojo.view.CollectionRelationViewPojo;
import eu.dariah.de.colreg.pojo.view.CollectionViewPojo;

@Component
public class CollectionRelationViewConverter extends BaseConverter<CollectionRelation, CollectionRelationViewPojo> {

	@Override
	public CollectionRelationViewPojo convertToPojo(CollectionRelation relation, Locale locale) {
		return this.convertToPojo(relation, locale, null);
	}

	public List<CollectionRelationViewPojo> convertToPojos(List<CollectionRelation> objects, Locale locale, Map<String, CollectionViewPojo> collectionEntityIdViewPojoMap) {
		if (objects==null || objects.isEmpty()) {
			return new ArrayList<CollectionRelationViewPojo>(0);
		}
		List<CollectionRelationViewPojo> pojos = new ArrayList<CollectionRelationViewPojo>(objects.size());
		for (CollectionRelation object : objects) {
			pojos.add(this.convertToPojo(object, locale, collectionEntityIdViewPojoMap));
		}
		return pojos;
	}
	
	public CollectionRelationViewPojo convertToPojo(CollectionRelation relation, Locale locale, Map<String, CollectionViewPojo> collectionEntityIdViewPojoMap) {
		CollectionRelationViewPojo pojo = new CollectionRelationViewPojo();
		pojo.setId(relation.getId());
		pojo.setBidirectional(relation.isBidirectional());
		pojo.setRelationTypeId(relation.getRelationTypeId());
		if (collectionEntityIdViewPojoMap!=null) {
			pojo.setSource(collectionEntityIdViewPojoMap.get(relation.getSourceEntityId()));
			pojo.setTarget(collectionEntityIdViewPojoMap.get(relation.getTargetEntityId()));
		}
		return pojo;
	}
	
}
