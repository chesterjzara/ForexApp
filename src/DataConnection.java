import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import io.github.cdimascio.dotenv.Dotenv;

public class DataConnection {
	//private String dbUrl = "jdbc:sqlite:C:\\data\\test_db.db";
	private String dbJDBC = "jdbc:sqlite:";
	private Connection connection;
	
	public DataConnection() {		
		// Edit .env file to set DATABASE_URL
		Dotenv dotenv = Dotenv.load();
		String dbFile = dotenv.get("DATABASE_URL");
		
		try {
			// Create DB connection - save to DataConnection object
			connection = DriverManager.getConnection(dbJDBC + dbFile);
			// Note - don't need to check if the DB exists - SQLite will create new DB
			
			// Check which tables exist - create if missing!
			checkTables(connection);
			
		} catch(SQLException e) {    
			System.out.println(e.getMessage());
        }
	}
	
	public Connection getConn() {
		return connection;
	}
	
	
	private static boolean checkTables(Connection connection) {
		ArrayList<String> tableNames = new ArrayList<String>();
		try {
			ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
			while (rs.next()) {
		        tableNames.add(rs.getString("TABLE_NAME"));
		        System.out.println("Check table - " + rs.getString("TABLE_NAME"));	// TODO - remove debuggin
			}
			
			// Create User Table
			if (!tableNames.contains("users")) {
				String sql = UserDAL.usersTableDDL;
				connection.createStatement().executeUpdate(sql);
				System.out.println("Created 'users' table in given database...");	// TODO - remove debuggin
			}
			
			if (!tableNames.contains("currency")) {
				connection.createStatement().executeUpdate(CurrencyDAL.currencyTableDLL);
				System.out.println("Created 'currency' table in given database...");	//TODO - remove debugging
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
}
