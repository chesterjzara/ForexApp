package Models;

public class CurrencyModel {
	private int symbolId;
	private String symbol;
	private String country;
	private double population;
	private double gdp;
	private double debt;
	private double birthRate;
	private double averageAge;
	private int billionaires;
	private int landArea;
	private double density;
	private boolean isLandlocked;
	private double goldReserves;
	private double milesHighway;
	private double maleObesity;
	private double femaleObesity;
	
	public CurrencyModel() {}
	
	public CurrencyModel(int symbolId, String symbol, String country, double population, double gdp, double debt,
			double birthRate, double averageAge, int billionaires, int landArea, double density, boolean isLandlocked,
			double goldReserves, double milesHighway, double maleObesity, double femaleObesity) {
		super();
		this.symbolId = symbolId;
		this.symbol = symbol;
		this.country = country;
		this.population = population;
		this.gdp = gdp;
		this.debt = debt;
		this.birthRate = birthRate;
		this.averageAge = averageAge;
		this.billionaires = billionaires;
		this.landArea = landArea;
		this.density = density;
		this.isLandlocked = isLandlocked;
		this.goldReserves = goldReserves;
		this.milesHighway = milesHighway;
		this.maleObesity = maleObesity;
		this.femaleObesity = femaleObesity;
	}

	/**
	 * @return the symbolId
	 */
	public int getSymbolId() {
		return symbolId;
	}

	/**
	 * @param symbolId the symbolId to set
	 */
	public void setSymbolId(int symbolId) {
		this.symbolId = symbolId;
	}

	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * @param symbol the symbol to set
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	
	/**
	 * @return the population
	 */
	public double getPopulation() {
		return population;
	}

	/**
	 * @param population the population to set
	 */
	public void setPopulation(double population) {
		this.population = population;
	}

	/**
	 * @return the gdp
	 */
	public double getGdp() {
		return gdp;
	}

	/**
	 * @param gdp the gdp to set
	 */
	public void setGdp(double gdp) {
		this.gdp = gdp;
	}
	
	/**
	 * @return the debt
	 */
	public double getDebt() {
		return debt;
	}

	/**
	 * @param debt the debt to set
	 */
	public void setDebt(double debt) {
		this.debt = debt;
	}

	/**
	 * @return the birthRate
	 */
	public double getBirthRate() {
		return birthRate;
	}

	/**
	 * @param birthRate the birthRate to set
	 */
	public void setBirthRate(double birthRate) {
		this.birthRate = birthRate;
	}

	/**
	 * @return the averageAge
	 */
	public double getAverageAge() {
		return averageAge;
	}

	/**
	 * @param averageAge the averageAge to set
	 */
	public void setAverageAge(double averageAge) {
		this.averageAge = averageAge;
	}

	/**
	 * @return the billionaires
	 */
	public int getBillionaires() {
		return billionaires;
	}

	/**
	 * @param billionaires the billionaires to set
	 */
	public void setBillionaires(int billionaires) {
		this.billionaires = billionaires;
	}

	/**
	 * @return the landArea
	 */
	public int getLandArea() {
		return landArea;
	}

	/**
	 * @param landArea the landArea to set
	 */
	public void setLandArea(int landArea) {
		this.landArea = landArea;
	}

	/**
	 * @return the density
	 */
	public double getDensity() {
		return density;
	}

	/**
	 * @param density the density to set
	 */
	public void setDensity(double density) {
		this.density = density;
	}

	/**
	 * @return the isLandlocked
	 */
	public boolean isLandlocked() {
		return isLandlocked;
	}

	/**
	 * @param isLandlocked the isLandlocked to set
	 */
	public void setLandlocked(boolean isLandlocked) {
		this.isLandlocked = isLandlocked;
	}

	/**
	 * @return the goldReserves
	 */
	public double getGoldReserves() {
		return goldReserves;
	}

	/**
	 * @param goldReserves the goldReserves to set
	 */
	public void setGoldReserves(double goldReserves) {
		this.goldReserves = goldReserves;
	}

	/**
	 * @return the milesHighway
	 */
	public double getMilesHighway() {
		return milesHighway;
	}

	/**
	 * @param milesHighway the milesHighway to set
	 */
	public void setMilesHighway(double milesHighway) {
		this.milesHighway = milesHighway;
	}

	/**
	 * @return the maleObesity
	 */
	public double getMaleObesity() {
		return maleObesity;
	}

	/**
	 * @param maleObesity the maleObesity to set
	 */
	public void setMaleObesity(double maleObesity) {
		this.maleObesity = maleObesity;
	}

	/**
	 * @return the femaleObesity
	 */
	public double getFemaleObesity() {
		return femaleObesity;
	}

	/**
	 * @param femaleObesity the femaleObesity to set
	 */
	public void setFemaleObesity(double femaleObesity) {
		this.femaleObesity = femaleObesity;
	}

	public String toString() {
		return "" + symbol + " [" + symbolId + "]"; 
	}
}
