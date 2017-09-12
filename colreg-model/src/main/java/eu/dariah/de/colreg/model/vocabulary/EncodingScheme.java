package eu.dariah.de.colreg.model.vocabulary;

import de.unibamberg.minf.dme.model.base.BaseIdentifiable;

public class EncodingScheme extends BaseIdentifiable {
	private static final long serialVersionUID = 8904831004241137209L;
	
	private String name;
	private String url;
	
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public String getUrl() { return url; }
	public void setUrl(String url) { this.url = url; }
}
