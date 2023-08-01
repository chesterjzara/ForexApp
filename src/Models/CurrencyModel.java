package Models;

public class CurrencyModel {
	private int id;
	private String name;
	private String acronym;
	private String symbol;
	private String jurisdiction;
	private int population;
	private double area;
	private double density;
	private double gdp;
	
	public CurrencyModel() {}
	
	public CurrencyModel(int id, String name, String acronym, String symbol, String jurisdication,
			int population, double area, double density, double gdp) {
		this.setId(id);
		this.setName(name);
		this.setAcronym(acronym);
		this.setSymbol(symbol);
		this.setJurisdiction(jurisdication);
		this.setPopulation(population);
		this.setArea(area);
		this.setDensity(density);
		this.setGdp(gdp);
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

	public String getAcronym() {
		return acronym;
	}

	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getJurisdiction() {
		return jurisdiction;
	}

	public void setJurisdiction(String jurisdiction) {
		this.jurisdiction = jurisdiction;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public double getDensity() {
		return density;
	}

	public void setDensity(double density) {
		this.density = density;
	}

	public double getGdp() {
		return gdp;
	}

	public void setGdp(double gdp) {
		this.gdp = gdp;
	}
	
	public String toString() {
		return "" + name + " [" + id + "] - " + acronym; 
	}
}
