package eu.dariah.de.colreg.model;

import de.dariah.aai.javasp.base.Role;

public class RoleImpl implements Role {
	private static final long serialVersionUID = 4139022217963525795L;

	private int level;
	
	private String authority;
	private String description;
	
	public RoleImpl() {}
	public RoleImpl(String authority) {
		this.authority = authority;
	}
	
	@Override public int getLevel() { return level; }
	public void setLevel(int level) { this.level = level; }

	@Override public String getAuthority() { return authority; }
	public void setAuthority(String authority) { this.authority = authority; }

	@Override public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }

	@Override public int getId() { return 0; }
}
