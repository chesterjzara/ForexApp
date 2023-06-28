
public class UserModel {
	private int id;
	private String name;
	private String email;
	private String hashPassword;
	
	public UserModel(int id, String name, String email, String hashPassword) {
		this.setId(id);
		this.setName(name);
		this.setEmail(email);
		this.setHashPassword(hashPassword);
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
	
	public String toString() {
		return " " + name + " [" + id + "] - " + email; 
	}
}
