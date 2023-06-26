import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAL {

	private DataConnection connector;
	
	public UserDAL(DataConnection connector) {
        this.connector = connector;
    }
	
	public UserModel retrieveData(int id) {
		
		String sql = "SELECT * FROM USERS u WHERE u.id=" + id;
		try {
			ResultSet rs = connector.getConn().createStatement().executeQuery(sql);
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
}
