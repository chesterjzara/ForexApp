package DAL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Models.*;

public class UserDAL {

	private DataConnection connection;
	public static String usersTableDDL = 
			"CREATE TABLE users ("
					+ "user_id  INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL,"
					+ "name TEXT, "
					+ "email TEXT, "
					+ "password TEXT,"
					+ "zip_code INTEGER);";
	
	public UserDAL(DataConnection connection) {
        this.connection = connection;
    }
	
	public UserModel getUser(int id) {
		String sql = "SELECT * FROM USERS u WHERE u.id=" + id;
		try {
			ResultSet rs = connection.getConn().createStatement().executeQuery(sql);
			rs.next();
			
			if (rs.getInt("id") > 0) {
				int userId = rs.getInt("id");
				String userName = rs.getString("name");
				String userEmail = rs.getString("email");
				String userHashPassword = rs.getString("hashPassword");
				
				return new UserModel(userId, userName, userEmail, userHashPassword);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public UserModel getUserByEmail(String inEmail) {
		String sql = "SELECT * FROM USERS u WHERE u.email=" + inEmail;

		try {
			ResultSet rs = connection.getConn().createStatement().executeQuery(sql);
			rs.next();
			
			if (rs.getInt("id") > 0) {
				int userId = rs.getInt("id");
				String userName = rs.getString("name");
				String userEmail = rs.getString("email");
				String userHashPassword = rs.getString("hashPassword");
				
				return new UserModel(userId, userName, userEmail, userHashPassword);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public boolean createUser(UserModel userInput) {
		String insertSql = "INSERT INTO users VALUES (?, ?, ?, ?);";
		int ret = 0;
		
		try {
			PreparedStatement pstmt = this.connection.getConn()
					.prepareStatement(insertSql);
			pstmt.setInt(1, userInput.getId());
			pstmt.setString(2, userInput.getName());
			pstmt.setString(3, userInput.getEmail());
			pstmt.setString(4, userInput.getHashPassword());
			
			ret = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (ret>0);
	}
	
	public UserModel updateUser(UserModel userInput) {
		//TODO
		return null;
	}
	
	public boolean deleteUser(int id) {
		//TODO
		return false;
	}
}
