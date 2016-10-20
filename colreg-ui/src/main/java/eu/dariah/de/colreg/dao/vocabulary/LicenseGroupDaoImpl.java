package eu.dariah.de.colreg.dao.vocabulary;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import eu.dariah.de.colreg.dao.base.BaseDaoImpl;
import eu.dariah.de.colreg.model.vocabulary.License;
import eu.dariah.de.colreg.model.vocabulary.LicenseGroup;

@Repository
public class LicenseGroupDaoImpl extends BaseDaoImpl<LicenseGroup> implements LicenseGroupDao {
	public LicenseGroupDaoImpl() {
		super(LicenseGroup.class);
	}

	@Override
	public List<License> findAllLicenses() {
		List<License> result = new ArrayList<License>();
		for (LicenseGroup lg : this.findAll()) {
			for (License l : lg.getLicenses()) {
				l.setGroup(lg);
				result.add(l);
			}
		}
		return result;
	}

	@Override
	public License findLicenseById(String licenseId) {
		
		Criteria c = Criteria.where("licenses").elemMatch(Criteria.where("_id").is(licenseId));
	    BasicQuery basicQuery = new BasicQuery(c.getCriteriaObject(), c.getCriteriaObject());

	    LicenseGroup lg = this.findOne(basicQuery);
	    if (lg!=null) {
	    	License g = lg.getLicenses().get(0);
	    	g.setGroup(lg);
	    	return g;
	    }	    
	    return null;
	}
}
