package DAL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import Models.*;

public class FriendDAL {
	private DataConnection connection;
	
	public static final String friendTableDLL = 
			"CREATE TABLE friend_bridge"
			+ "user_id INTEGER PRIMARY KEY NOT NULL,"
			+ "friend_id INTEGER REFERENCES users (user_id));";
	
	public FriendDAL(DataConnection connection) {
        this.connection = connection;
    }
	
	public ArrayList<UserModel> getUsersFriends (int userId) {
		String sql = "SELECT * FROM friend_bridge WHERE user_id= ?";
		ArrayList<UserModel> friendUsers = new ArrayList<UserModel>();
		
		try {
			PreparedStatement ps = this.connection.getConn().prepareStatement(sql);
			ps.setInt(1, userId);
			
			// Might not be able to do prepared statement here?
			ResultSet rs = ps.executeQuery(sql);
			
			while(rs.next()) {
				if (rs.getInt("user_id") > 0) {
					FriendModel f = parseFriendResult(rs);
					
					UserModel u = connection.userDAL.getUser(f.getFriendId());
					friendUsers.add(u);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return friendUsers;
	}
	
	public FriendModel createFriend(FriendModel f) {
		String insertSql = "INSERT INTO friend_bridge VALUES (?, ?);";
		
		try {
			PreparedStatement ps = this.connection.getConn().prepareStatement(insertSql);
			ps.setInt(1, f.getUserId());
			ps.setInt(2, f.getFriendId());
			
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				return f;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean deleteFriend(int userId, int friendId) {
		String deleteSql = "DELETE FROM friend_bridge WHERE user_id= ? AND friend_id=?";
		int ret = 0;
		
		try {
			PreparedStatement ps = this.connection.getConn().prepareStatement(deleteSql);
			ps.setInt(1, userId);
			ps.setInt(2, friendId);
			
			ret = ps.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return (ret > 0);
	}
	
	public FriendModel parseFriendResult(ResultSet rs) { 
		FriendModel f = new FriendModel();
		try {
			f.setUserId(rs.getInt("user_id"));
			f.setFriendId(rs.getInt("friend_id"));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return f;
	}
}
