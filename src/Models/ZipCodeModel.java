package Models;

public class ZipCodeModel {
	private int zipCode;
	private String state;
	private String stateAbbr;
	private String country;
	private String city;
	
	public ZipCodeModel() {
		super();
	}

	public ZipCodeModel(int zipCode, String state, String stateAbbr, String country, String city) {
		super();
		this.zipCode = zipCode;
		this.state = state;
		this.stateAbbr = stateAbbr;
		this.country = country;
		this.city = city;
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

	public String getStateAbbr() {
		return stateAbbr;
	}

	public void setStateAbbr(String stateAbbr) {
		this.stateAbbr = stateAbbr;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	
}
