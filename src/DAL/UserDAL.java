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
				
				int zipCodeId = rs.getInt("zip_code");
				ZipCodeModel zipCode = connection.zipCodeDAL.getZipCode(zipCodeId);
				
				
				return new UserModel(userId, userName, userEmail, userHashPassword, zipCode);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public UserModel getUserByEmail(String inEmail) {
		String sql = "SELECT * FROM USERS u WHERE u.email='" + inEmail + "'";

		try {
			ResultSet rs = connection.getConn().createStatement().executeQuery(sql);
			rs.next();
			
			if (rs.getInt("user_id") > 0) {
				int userId = rs.getInt("user_id");
				String userName = rs.getString("name");
				String userEmail = rs.getString("email");
				String userHashPassword = rs.getString("password");
				ZipCodeModel zipCode = connection.zipCodeDAL.getZipCode(rs.getInt("zip_code"));
				
				return new UserModel(userId, userName, userEmail, userHashPassword, zipCode);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public UserModel checkUserLogin(String email, String password) {
		UserModel loginUser = getUserByEmail(email);
		
		if (loginUser != null) {
			if (loginUser.getHashPassword().equals(password)) {
				return loginUser;
			}
		}
		return null;
	}
	
	public UserModel createUser(UserModel userInput) {
		String insertSql = "INSERT INTO users VALUES (NULL, ?, ?, ?, ?);";
		
		try {
			PreparedStatement pstmt = this.connection.getConn()
					.prepareStatement(insertSql);
//			pstmt.setInt(1, userInput.getId());
			pstmt.setString(1, userInput.getName());
			pstmt.setString(2, userInput.getEmail());
			pstmt.setString(3, userInput.getHashPassword());
			pstmt.setInt(4, userInput.getZipCode().getZipCode());
			
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				int newUserId = rs.getInt(1);
				userInput.setId(newUserId);
				return userInput;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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
