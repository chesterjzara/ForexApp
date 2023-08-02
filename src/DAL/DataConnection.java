package DAL;

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
	private String dbFilePath; 
	private Connection connection;
	
	public DataConnection() {		
		// Edit .env file to set DATABASE_URL
		Dotenv dotenv = Dotenv.load();
		String dbFile = dotenv.get("DATABASE_URL");
		// example: jdbc:sqlite:C:\\data\\test_db2.db
		dbFilePath = dbJDBC + dbFile;
		
		try {
			// Create DB connection - save to DataConnection object
			connection = DriverManager.getConnection(dbFilePath);
			// Note - don't need to check if the DB exists - SQLite will create new DB
			
//			// Check which tables exist - create if missing!
//			checkTables(connection);
			
		} catch(SQLException e) {    
			System.out.println(e.getMessage());
        }
	}
	
	public Connection getConn() {
		return connection;
	}
	
	public String getDbFilePath() {
		return dbFilePath;
	}
	
	public void closeDataConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static boolean checkCreateTables(Connection connection) {
		ArrayList<String> tableNames = new ArrayList<String>();
		try {
			ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
			while (rs.next()) {
		        tableNames.add(rs.getString("TABLE_NAME"));
		        // System.out.println("Check table - " + rs.getString("TABLE_NAME"));	
			}
			
			// Create currency table
			if (!tableNames.contains("currency")) {
				String sql = CurrencyDAL.currencyTableDLL;
				connection.createStatement().executeUpdate(sql);
				System.out.println("Created 'currency' table in given database...");
			} else {
				System.out.println("Table exists for 'currency'");
			}
			
			// Create exchange table
			if (!tableNames.contains("exchange_rate")) {
				String sql = ExchangeRateDAL.exchangeRateTableDLL;
				connection.createStatement().executeUpdate(sql);
				System.out.println("Created 'exchange_rate' table in given database...");
			} else {
				System.out.println("Table exists for 'exchange_rate'");
			}
			
			// Create users_favorites table
			if (!tableNames.contains("user_favorites")) {
				String sql = UserFavoriteDAL.userFavoriteTableDLL;
				connection.createStatement().executeUpdate(sql);
				System.out.println("Created 'user_favorites' table in given database...");	
			} else {
				System.out.println("Table exists for 'user_favorites'");
			}
			
			// Create users table
			if (!tableNames.contains("users")) {
				String sql = UserDAL.usersTableDDL;
				connection.createStatement().executeUpdate(sql);
				System.out.println("Created 'users' table in given database...");	
			} else {
				System.out.println("Table exists for 'users'");
			}
			
			// Create users table
			if (!tableNames.contains("zip_code")) {
				String sql = ZipCodeDAL.zipCodeTableDDL;
				connection.createStatement().executeUpdate(sql);
				System.out.println("Created 'zip_code' table in given database...");	
			} else {
				System.out.println("Table exists for 'zip_code'");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
}
