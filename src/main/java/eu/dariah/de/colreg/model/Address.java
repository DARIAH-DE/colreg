package eu.dariah.de.colreg.model;

public class Address {
	private String street;
	private String number;
	private String postalCode;
	private String place;
	private String country;
	private String note;
	
	public String getStreet() { return street; }
	public void setStreet(String street) { this.street = street; }
	
	public String getNumber() { return number; }
	public void setNumber(String number) { this.number = number; }
	
	public String getPostalCode() { return postalCode; }
	public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
	
	public String getPlace() { return place; }
	public void setPlace(String place) { this.place = place; }
	
	public String getCountry() { return country; }
	public void setCountry(String country) { this.country = country; }
	
	public String getNote() { return note; }
	public void setNote(String note) { this.note = note; }
	
	public boolean isEmpty() {
		return ((this.getStreet()==null || this.getStreet().trim().isEmpty()) && 
				(this.getNumber()==null || this.getNumber().trim().isEmpty()) && 
				(this.getPostalCode()==null || this.getPostalCode().trim().isEmpty()) && 
				(this.getPlace()==null || this.getPlace().trim().isEmpty()) && 
				(this.getCountry()==null || this.getCountry().trim().isEmpty()) && 
				(this.getNote()==null || this.getNote().trim().isEmpty()) );
	}
}