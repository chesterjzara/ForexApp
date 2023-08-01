package DAL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Models.*;

public class CurrencyDAL {
	
	private DataConnection connection;
	
	public static final String currencyTableDLL = 
			"CREATE TABLE currency ("
					+ "id INTEGER PRIMARY KEY,"
					+ "name TEXT,"
					+ "acronym TEXT,"
					+ "symbol TEXT,"
					+ "jurisdiction TEXT,"
					+ "population INTEGER,"
					+ "area NUMERIC,"
					+ "density NUMERIC,"
					+ "gdp NUMERIC)";
	
	public CurrencyDAL(DataConnection connection) {
        this.connection = connection;
    }
	
	public CurrencyModel getCurrency(int id) {
		String sql = "SELECT * FROM currency c WHERE c.id=" + id;
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
	
	private CurrencyModel parseCurrencyResult(ResultSet rs) {
		CurrencyModel c = new CurrencyModel();
		try {
			c.setId(rs.getInt("id"));
			c.setName(rs.getString("name"));
			c.setAcronym(rs.getString("acronym"));
			c.setSymbol(rs.getString("symbol"));
			c.setJurisdiction(rs.getString("jurisdiction"));
			c.setPopulation(rs.getInt("population"));
			c.setArea(rs.getDouble("area"));
			c.setDensity(rs.getDouble("density"));
			c.setGdp(rs.getDouble("gdp"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}
}
