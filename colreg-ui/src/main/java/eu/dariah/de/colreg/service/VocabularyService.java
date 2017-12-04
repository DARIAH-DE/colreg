package eu.dariah.de.colreg.service;

import java.util.List;

import eu.dariah.de.colreg.model.vocabulary.AccessType;
import eu.dariah.de.colreg.model.vocabulary.AccrualMethod;
import eu.dariah.de.colreg.model.vocabulary.AccrualPeriodicity;
import eu.dariah.de.colreg.model.vocabulary.AccrualPolicy;
import eu.dariah.de.colreg.model.vocabulary.AgentRelationType;
import eu.dariah.de.colreg.model.vocabulary.AgentType;
import eu.dariah.de.colreg.model.vocabulary.ItemType;
import eu.dariah.de.colreg.model.vocabulary.Language;
import eu.dariah.de.colreg.model.vocabulary.UnitOfMeasurement;

public interface VocabularyService {
	public List<Language> queryLanguages(String query);

	public List<AccrualMethod> findAllAccrualMethods();
	public List<AccrualPeriodicity> findAllAccrualPeriodicities();
	public List<AccrualPolicy> findAllAccrualPolicies();
	public List<AccessType> findAllAccessTypes();
	public List<AgentType> findAllAgentTypes();
	public List<AgentRelationType> findAllAgentRelationTypes();
	public List<ItemType> findAllItemTypes();
	
	public ItemType findItemTypeById(String id);
	public ItemType findItemTypeByIdentifier(String id);
	
	public Language findLanguageById(String id);
	public Language findLanguageByCode(String id);
	public AccessType findAccessTypeById(String id);
	public AccessType findAccessTypeByIdentfier(String string);
	public AgentType findAgentTypeById(String agentTypeId);

	public List<UnitOfMeasurement> findAllUnitsOfMeasurement();
	public UnitOfMeasurement findUnitOfMeasurementByName(String uom);
	public void saveUnitOfMeasurement(UnitOfMeasurement unit);
}
