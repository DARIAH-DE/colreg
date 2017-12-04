package eu.dariah.de.colreg.dao.vocabulary;

import org.springframework.stereotype.Repository;

import eu.dariah.de.colreg.dao.base.BaseDaoImpl;
import eu.dariah.de.colreg.model.vocabulary.UnitOfMeasurement;

@Repository
public class UnitOfMeasurementDaoImpl extends BaseDaoImpl<UnitOfMeasurement> implements UnitOfMeasurementDao {
	public UnitOfMeasurementDaoImpl() {
		super(UnitOfMeasurement.class);
	}
}
