package Models;

public class UserModel {
	private int id;
	private String name;
	private String email;
	private String hashPassword;
	private int zipCodeId;
	private ZipCodeModel zipCode;
	
	public UserModel() {}
	
	public UserModel(int id, String name, String email, String hashPassword, 
			ZipCodeModel zipCode) {
		this.setId(id);
		this.setName(name);
		this.setEmail(email);
		this.setHashPassword(hashPassword);
		this.setZipCode(zipCode);
	}
	
	public UserModel(int id, String name, String email, String hashPassword, 
			int zipCodeId) {
		this.setId(id);
		this.setName(name);
		this.setEmail(email);
		this.setHashPassword(hashPassword);
		this.setZipCodeId(zipCodeId);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHashPassword() {
		return hashPassword;
	}

	public void setHashPassword(String hashPassword) {
		this.hashPassword = hashPassword;
	}
	
	public int getZipCodeId() {
		return zipCodeId;
	}

	public void setZipCodeId(int zipCodeId) {
		this.zipCodeId = zipCodeId;
	}

	public ZipCodeModel getZipCode() {
		return zipCode;
	}

	public void setZipCode(ZipCodeModel zipCode) {
		this.zipCode = zipCode;
	}

	public String toString() {
		return " " + name + " [" + id + "] - " + email; 
	}
	
	public String fullInfo() {
		ZipCodeModel z = this.getZipCode();
		return String.format("%s (%s) in %s, %s - %s County, %d [%d]", name, email, 
				z.getCity(), z.getStateAbbr(), z.getCountry(), z.getZipCode(), id);
	}
}
