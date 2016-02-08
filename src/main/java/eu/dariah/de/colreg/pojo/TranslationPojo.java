package eu.dariah.de.colreg.pojo;

public class TranslationPojo {
	private String key;
	private String translation;
	private String[] args;

	public String getKey() { return key; }
	public void setKey(String key) { this.key = key; }
	
	public String getTranslation() { return translation; }
	public void setTranslation(String translation) { this.translation = translation; }		
		
	public String[] getArgs() { return args; }
	public void setArgs(String[] args) { this.args = args; }
}
