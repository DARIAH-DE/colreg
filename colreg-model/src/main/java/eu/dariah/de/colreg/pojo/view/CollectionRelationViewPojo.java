package eu.dariah.de.colreg.pojo.view;

import de.unibamberg.minf.dme.model.base.BaseIdentifiable;

public class CollectionRelationViewPojo extends BaseIdentifiable {
	private static final long serialVersionUID = -596585175519103225L;
	
	private CollectionViewPojo source;
	private CollectionViewPojo target;
	private boolean bidirectional;
	private String relationTypeId;
	private String description;
	
	
	public CollectionViewPojo getSource() { return source; }
	public void setSource(CollectionViewPojo source) { this.source = source; }
	
	public CollectionViewPojo getTarget() { return target; }
	public void setTarget(CollectionViewPojo target) { this.target = target; }
	
	public boolean isBidirectional() { return bidirectional; }
	public void setBidirectional(boolean bidirectional) { this.bidirectional = bidirectional; }
	
	public String getRelationTypeId() { return relationTypeId; }
	public void setRelationTypeId(String relationTypeId) { this.relationTypeId = relationTypeId; }	
	
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
}