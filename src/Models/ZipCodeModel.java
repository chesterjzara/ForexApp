package Models;

public class ZipCodeModel {
	private int zipCode;
	private String city;
	private String state;
	
	public ZipCodeModel() {
		super();
	}

	public ZipCodeModel(int zipCode, String city, String state) {
		super();
		this.zipCode = zipCode;
		this.city = city;
		this.state = state;
	}

	/**
	 * @return the zipCode
	 */
	public int getZipCode() {
		return zipCode;
	}

	/**
	 * @param zipCode the zipCode to set
	 */
	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	
	
}
