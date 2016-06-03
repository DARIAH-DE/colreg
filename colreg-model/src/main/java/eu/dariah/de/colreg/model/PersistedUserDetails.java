package eu.dariah.de.colreg.model;

import org.joda.time.DateTime;

import de.dariah.aai.javasp.base.SimpleUserDetails;
import eu.dariah.de.colreg.model.base.Identifiable;

public class PersistedUserDetails extends SimpleUserDetails implements Identifiable {
	private static final long serialVersionUID = -6763023924626676185L;
	
	private DateTime lastLogin;

	public DateTime getLastLogin() { return lastLogin; }
	public void setLastLogin(DateTime lastLogin) { this.lastLogin = lastLogin; }
}
