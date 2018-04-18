package eu.dariah.de.colreg.service;

import java.util.List;

import eu.dariah.de.colreg.model.vocabulary.UnitOfMeasurement;

public interface UnitOfMeasurementService {
	public List<UnitOfMeasurement> findAllUnitsOfMeasurement();
	public UnitOfMeasurement findUnitOfMeasurementByName(String uom);
	public void saveUnitOfMeasurement(UnitOfMeasurement unit);
}
