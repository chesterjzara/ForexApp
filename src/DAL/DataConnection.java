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
	
	public ZipCodeDAL zipCodeDAL;
	public UserDAL userDAL;
	
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
			zipCodeDAL = new ZipCodeDAL(this);
			userDAL = new UserDAL(this);
			
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
}
