package DAL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

import Models.*;

public class CurrencyDAL {
	
	private DataConnection connection;
	
	public static Dictionary<String, String> dict = new Hashtable<String, String>() {{
		put("symbolId", "symbol_id");
		put("symbol", "symbol");
        put("country", "country");
        put("population", "population");
        put("gdp", "gdp");
        put("debt", "debt");
        put("birthRate", "birth_rate");
        put("averageAge", "average_age");
        put("billionaires", "billionaires");
        put("landArea", "land_area");
        put("density", "density");
        put("isLandlocked", "is_landlocked");
        put("goldReserves", "gold_reserves");
        put("milesHighway", "miles_highway");
        put("maleObesity", "male_obesity");
        put("femaleObesity", "female_obesity");
	}};
	
	public static final String currencyTableDLL = 
			"CREATE TABLE currency ("
				    + "symbol_id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL,"
				    + "symbol         TEXT,"
				    + "country        TEXT,"
				    + "population     NUMERIC,"
				    + "gdp            NUMERIC,"
				    + "debt           NUMERIC,"
				    + "birth_rate     NUMERIC,"
				    + "average_age    NUMERIC,"
				    + "billionaires   INTEGER,"
				    + "land_area      INTEGER,"
				    + "density        NUMERIC,"
				    + "is_landlocked  INTEGER,"
				    + "gold_reserves  NUMERIC,"
				    + "miles_highway  NUMERIC,"
				    + "male_obesity   NUMERIC,"
				    + "female_obesity NUMERIC);";
	
	public CurrencyDAL(DataConnection connection) {
        this.connection = connection;
    }
	
	public CurrencyModel getCurrency(int symbolId) {
		String sql = "SELECT * FROM currency c WHERE c.symbol_id=" + symbolId;
		try {
			ResultSet rs = connection.getConn().createStatement().executeQuery(sql);
			rs.next();
			CurrencyModel currency = parseCurrencyResult(rs);
			return currency;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public int getCurrencyIdFromSymbol(String symbol) {
		String sql = "SELECT c.symbol_id FROM currency c WHERE c.symbol='"+ symbol + "'";
		
		try {
			ResultSet rs = connection.getConn().createStatement().executeQuery(sql);
			rs.next();
			return rs.getInt("symbol_id");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public ArrayList<CurrencyModel> getCurrencyList() {
		String sql = "SELECT * FROM currency c;";
		ArrayList<CurrencyModel> currencyList = new ArrayList<CurrencyModel>();
		try {
			ResultSet rs = connection.getConn().createStatement()
					.executeQuery(sql);
			while(rs.next()) {
				CurrencyModel curr = parseCurrencyResult(rs);
				currencyList.add(curr);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (currencyList.size() > 0) {
			return currencyList;
		}
		return null;
	}
	
	public boolean createCurrency(CurrencyModel c) {
		// Null for first value to auto increment symbol_id
		String insertSql = "INSERT INTO currency "
				+ "VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		int ret = 0;
		
		try {
			PreparedStatement p = this.connection.getConn()
					.prepareStatement(insertSql);
			p.setString(1, c.getSymbol());
			p.setString(2, c.getCountry());
			p.setDouble(3, c.getPopulation());
			p.setDouble(4, c.getGdp());
			p.setDouble(5, c.getDebt());
			p.setDouble(6, c.getBirthRate());
			p.setDouble(7, c.getAverageAge());
			p.setInt(8, c.getBillionaires());
			p.setInt(9, c.getLandArea());
			p.setDouble(10, c.getDensity());
			p.setBoolean(11, c.isLandlocked());
			p.setDouble(12, c.getGoldReserves());
			p.setDouble(13, c.getMilesHighway());
			p.setDouble(14, c.getMaleObesity());
			p.setDouble(15, c.getFemaleObesity());
			
			ret = p.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (ret>0);
	}
	
	public static CurrencyModel parseCurrencyResult(ResultSet rs) {
		CurrencyModel c = new CurrencyModel();
		try {
			c.setSymbolId(rs.getInt(dict.get("symbolId")));
			c.setSymbol(rs.getString(dict.get("symbol")));
			c.setCountry(rs.getString(dict.get("country")));
			c.setPopulation(rs.getInt("population"));
			c.setGdp(rs.getDouble("gdp"));
			c.setDebt(rs.getDouble(dict.get("debt")));
			c.setBirthRate(rs.getDouble(dict.get("birthRate")));
			c.setAverageAge(rs.getDouble(dict.get("averageAge")));
			c.setBillionaires(rs.getInt(dict.get("billionaires")));
			c.setLandArea(rs.getInt(dict.get("landArea")));
			c.setDensity(rs.getDouble(dict.get("density")));
			c.setLandlocked(rs.getBoolean(dict.get("isLandlocked")));
			c.setGoldReserves(rs.getDouble(dict.get("goldReserves")));
			c.setMilesHighway(rs.getDouble(dict.get("milesHighway")));
			c.setMaleObesity(rs.getDouble(dict.get("maleObesity")));
			c.setFemaleObesity(rs.getDouble(dict.get("femaleObesity")));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}
}
