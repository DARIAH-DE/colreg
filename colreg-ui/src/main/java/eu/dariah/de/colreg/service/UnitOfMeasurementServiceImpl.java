package eu.dariah.de.colreg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import eu.dariah.de.colreg.dao.vocabulary.UnitOfMeasurementDao;
import eu.dariah.de.colreg.model.vocabulary.UnitOfMeasurement;

@Service
public class UnitOfMeasurementServiceImpl implements UnitOfMeasurementService {
	@Autowired private UnitOfMeasurementDao uomDao;
	
	@Override
	public List<UnitOfMeasurement> findAllUnitsOfMeasurement() {
		return uomDao.findAll();
	}

	@Override
	public UnitOfMeasurement findUnitOfMeasurementByName(String uom) {
		return uomDao.findOne(Query.query(Criteria.where("name").is(uom.trim())));
	}

	@Override
	public void saveUnitOfMeasurement(UnitOfMeasurement unit) {
		uomDao.save(unit);
	}
}
