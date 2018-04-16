package eu.dariah.de.colreg.pojo.api;

public class EdgePojo {
	private String source;
	private String target;
	
	
	public String getSource() { return source; }
	public void setSource(String source) { this.source = source; }
	
	public String getTarget() { return target; }
	public void setTarget(String target) { this.target = target; }
		
	
	@Override
	public boolean equals(Object obj) {
		return source.equals(target);
	}
}
