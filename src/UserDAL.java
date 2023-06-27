import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAL {

	private DataConnection connection;
	public static String usersTableDDL = "CREATE TABLE users ("
											+ "id INTEGER PRIMARY KEY,"
											+ "age INTEGER, "
											+ "first TEXT, "
											+ "last TEXT)";
	
	public UserDAL(DataConnection connection) {
        this.connection = connection;
    }
	
	public UserModel retrieveUser(int id) {
		
		String sql = "SELECT * FROM USERS u WHERE u.id=" + id;
		try {
			ResultSet rs = connection.getConn().createStatement().executeQuery(sql);
			rs.next();
			
			int userId = rs.getInt("id");
			int userAge = rs.getInt("age");
			String userFirst = rs.getString("first");
			String userLast = rs.getString("last");
			
			return new UserModel(userId, userAge, userFirst, userLast);
			
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
			pstmt.setInt(2, userInput.getAge());
			pstmt.setString(3, userInput.getFirst());
			pstmt.setString(4, userInput.getLast());
			
			ret = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return (ret>0);
	}
}
