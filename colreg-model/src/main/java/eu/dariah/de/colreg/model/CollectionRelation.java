package eu.dariah.de.colreg.model;

import de.unibamberg.minf.dme.model.base.BaseIdentifiable;

public class CollectionRelation extends BaseIdentifiable {
	private static final long serialVersionUID = 1157156390776127985L;

	public static final String COLLECTION_RELATION_TYPES_VOCABULARY_IDENTIFIER = "collectionRelationTypes";
	
	private String sourceEntityId;
	private String targetEntityId;
	private boolean bidirectional;
	private String relationTypeId;
	
	
	public String getSourceEntityId() { return sourceEntityId; }
	public void setSourceEntityId(String sourceEntityId) { this.sourceEntityId = sourceEntityId; }
	
	public String getTargetEntityId() { return targetEntityId; }
	public void setTargetEntityId(String targetEntityId) { this.targetEntityId = targetEntityId; }
	
	public boolean isBidirectional() { return bidirectional; }
	public void setBidirectional(boolean bidirectional) { this.bidirectional = bidirectional; }
	
	public String getRelationTypeId() { return relationTypeId; }
	public void setRelationTypeId(String relationTypeId) { this.relationTypeId = relationTypeId; }
}