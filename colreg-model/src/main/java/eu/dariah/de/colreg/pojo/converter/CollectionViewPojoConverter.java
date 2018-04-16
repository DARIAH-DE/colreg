package eu.dariah.de.colreg.pojo.converter;

import java.util.Locale;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

import eu.dariah.de.colreg.model.Collection;
import eu.dariah.de.colreg.pojo.converter.base.BaseConverter;
import eu.dariah.de.colreg.pojo.view.CollectionViewPojo;

@Component
public class CollectionViewPojoConverter extends BaseConverter<Collection, CollectionViewPojo> {

	DateTimeFormatter fmt = DateTimeFormat.forStyle("LL");
	
	//@Autowired private AccessTypeDao accessTypeDao;
	
	@Override
	public CollectionViewPojo convertToPojo(Collection collection, Locale locale) {
		CollectionViewPojo pojo = new CollectionViewPojo();
		pojo.setId(collection.getId());
		
		pojo.setCollectionTypeIdentifiers(collection.getCollectionTypes());

		pojo.setTimestamp(collection.getVersionTimestamp().toInstant().getMillis());
		pojo.setDisplayTimestamp(fmt.withLocale(locale).print(collection.getVersionTimestamp()));
		
		/*pojo.setAccessTypes(collection.getacc);
		
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
		*/
		return pojo;
	}

}
