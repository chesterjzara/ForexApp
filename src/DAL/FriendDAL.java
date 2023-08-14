package DAL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import Models.*;
import application.FriendTableRow;

public class FriendDAL {
	private DataConnection connection;
	
	public static final String friendTableDLL = 
			"CREATE TABLE friend_bridge ("
			+ "user_id INTEGER  REFERENCES users (user_id),"
			+ "friend_id INTEGER REFERENCES users (user_id),"
			+ "PRIMARY KEY (user_id, friend_id));";
	
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
	
	public static final String getFriendList = 
			"SELECT f.user_id, f.friend_id, u.name, u.email, z.city, count(uf.user_favorite_id) 'fav_count'\n"
			+ "FROM friend_bridge f\n"
			+ "JOIN users u on f.friend_id = u.user_id\n"
			+ "JOIN user_favorites uf on uf.user_id = f.friend_id\n"
			+ "JOIN zip_code z on u.zip_code = z.zip_code\n"
			+ "WHERE f.user_id = ?\n"
			+ "GROUP BY friend_id, u.name, u.email;";
	
	public ArrayList<FriendTableRow> getFriendList(int userId) {
		ArrayList<FriendTableRow> friendList = new ArrayList<FriendTableRow>();
		
		try {
			PreparedStatement ps = this.connection.getConn().prepareStatement(getFriendList);
			ps.setInt(1, userId);
			
			// Might not be able to do prepared statement here?
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				if (rs.getInt("friend_id") > 0) {
					FriendTableRow newRow = new FriendTableRow();
					newRow.friendId = rs.getInt("friend_id");
					newRow.friendName = rs.getString("name");
					newRow.friendEmail = rs.getString("email");
					newRow.friendCity = rs.getString("city");
					newRow.numFavs = rs.getInt("fav_count");
					
					friendList.add(newRow);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return friendList;
	}
	
	public static final String getFriendRow = 
			"SELECT f.user_id, f.friend_id, u.name, u.email, z.city, count(uf.user_favorite_id) 'fav_count'\n"
			+ "FROM friend_bridge f\n"
			+ "JOIN users u on f.friend_id = u.user_id\n"
			+ "JOIN user_favorites uf on uf.user_id = f.friend_id\n"
			+ "JOIN zip_code z on u.zip_code = z.zip_code\n"
			+ "WHERE f.user_id = ?  AND f.friend_id = ?\n"
			+ "GROUP BY friend_id, u.name, u.email;";
	
	public FriendTableRow getFriendRow(int userId, int friendId) {
		FriendTableRow friendRow = new FriendTableRow();
		
		try {
			PreparedStatement ps = this.connection.getConn().prepareStatement(getFriendRow);
			ps.setInt(1, userId);
			ps.setInt(2, friendId);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.getInt("friend_id") > 0) {
				friendRow.friendId = rs.getInt("friend_id");
				friendRow.friendName = rs.getString("name");
				friendRow.friendEmail = rs.getString("email");
				friendRow.friendCity = rs.getString("city");
				friendRow.numFavs = rs.getInt("fav_count");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return friendRow;
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
