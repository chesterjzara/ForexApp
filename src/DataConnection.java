import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataConnection {
	
	private String dbUrl = "jdbc:sqlite:C:\\data\\test_db";
	private Connection connection;
	
	public DataConnection() {		
		try {
			connection = DriverManager.getConnection(dbUrl);
		
		} catch(SQLException e) {    
			System.out.println(e.getMessage());
        }
	}
	
	public Connection getConn() {
		return connection;
	}
}
