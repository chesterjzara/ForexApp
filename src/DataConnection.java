import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DataConnection {
	
	//private String dbUrl = "jdbc:sqlite:*\\test_db";  	// trying to use relative file
	private String dbUrl = "jdbc:sqlite:C:\\data\\test_db.db";
	private Connection connection;
	
	public DataConnection() {		
		try {
			connection = DriverManager.getConnection(dbUrl);
			
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
		        System.out.println("Check table - " + rs.getString("TABLE_NAME"));
			}
			
			// Create User Table
			if (!tableNames.contains("users")) {
				String sql = UserDAL.usersTableDDL;
				Statement stmt = connection.createStatement();
				stmt.executeUpdate(sql);
				System.out.println("Created table in given database...");
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
}
