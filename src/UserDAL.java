
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAL {

	private DataConnection connection;
	public static String usersTableDDL = "CREATE TABLE users ("
											+ "id INTEGER PRIMARY KEY,"
											+ "name TEXT, "
											+ "email TEXT, "
											+ "hashPassword TEXT)";
	
	public UserDAL(DataConnection connection) {
        this.connection = connection;
    }
	
	public UserModel retrieveUser(int id) {
		
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
	
	public boolean createUser(UserModel userInput) {
		String insertSql = "INSERT INTO users VALUES (?, ?, ?, ?);";
		int ret = 0;
		try {
//			String sql = "INSERT INTO users VALUES (12, 20, 'Test', 'Tester');";
//			ret = connection.getConn().createStatement().executeUpdate(sql);
			
			PreparedStatement pstmt = this.connection.getConn().prepareStatement(insertSql);
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
}
