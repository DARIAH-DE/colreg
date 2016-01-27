package eu.dariah.de.colreg.model.base;

public abstract class BaseIdentifiable implements Identifiable {
	private static final long serialVersionUID = 73456305634830558L;

	private String id;
	
		
	@Override public String getId() { return id; }
	@Override public void setId(String id) { this.id = id; }
}
