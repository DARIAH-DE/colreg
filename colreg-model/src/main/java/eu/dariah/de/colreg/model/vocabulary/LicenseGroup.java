package eu.dariah.de.colreg.model.vocabulary;

import java.util.List;
import java.util.Map;

import de.unibamberg.minf.dme.model.base.BaseIdentifiable;

public class LicenseGroup extends BaseIdentifiable {
	private static final long serialVersionUID = 3221594182077684205L;
	
	private List<License> licenses;
	private String identifier;
	private Map<String, String> labels;
	
	
	public List<License> getLicenses() { return licenses; }
	public void setLicenses(List<License> licenses) { this.licenses = licenses; }
	
	public String getIdentifier() { return identifier; }
	public void setIdentifier(String identifier) { this.identifier = identifier; }
	
	public Map<String, String> getLabels() { return labels; }
	public void setLabels(Map<String, String> labels) { this.labels = labels; }	
}