import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	
	public CurrencyModel retrieveCurrency(int id) {
		
		String sql = "SELECT * FROM currency c WHERE c.id=" + id;
		
		try {
			ResultSet rs = connection.getConn().createStatement().executeQuery(sql);
			rs.next();
			
			int cId = rs.getInt("id");
			String cName = rs.getString("name");
			String cAcronym = rs.getString("acronym");
			String cSymbol = rs.getString("symbol");
			String cJurisdiction= rs.getString("jurisdiction");
			int cPopulation = rs.getInt("population");
			double cArea = rs.getDouble("area");
			double cDensity = rs.getDouble("density");;
			double cGDP = rs.getDouble("gdp");;
			
			return new CurrencyModel(cId, cName, cAcronym, cSymbol, cJurisdiction,
					cPopulation, cArea, cDensity, cGDP);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
			
}
