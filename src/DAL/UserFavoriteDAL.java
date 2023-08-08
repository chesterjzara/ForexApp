package DAL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import Models.*;

public class UserFavoriteDAL {
	private DataConnection connection;
	
	public static final String userFavoriteTableDLL = 
			"CREATE TABLE user_favorites ("
					+ "user_favorite_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE,"
					+ "user_id INTEGER REFERENCES users (user_id),"
				    + "base_exchange_id INTEGER REFERENCES exchange_rate (exchange_id),"
				    + "target_exchange_id INTEGER REFERENCES exchange_rate (exchange_id),"
				    + "date_added TEXT);";
	
	public UserFavoriteDAL(DataConnection connection) {
        this.connection = connection;
    }
	
	public ArrayList<UserFavoriteModel> getUserFavoritesByUserId(int userId) {
		String sql = "SELECT * FROM user_favorites uf WHERE uf.user_id=" + userId;
		ArrayList<UserFavoriteModel> userFavoriteList = new ArrayList<UserFavoriteModel>();
		try {
			ResultSet rs = connection.getConn().createStatement()
					.executeQuery(sql);
			
			//rs.next();
			
			while(rs.next()) {
				if (rs.getInt("user_favorite_id") > 0) {
					UserFavoriteModel uf = parseUserFavoriteResult(rs);
					userFavoriteList.add(uf);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userFavoriteList;
	}
	
	public UserFavoriteModel createUserFavorite(UserFavoriteModel uf) {
		String insertSql = "INSERT INTO user_favorites VALUES (NULL, ?, ?, ?, ?);";
		
		try {
			PreparedStatement ps = this.connection.getConn().prepareStatement(insertSql);
			ps.setInt(1, uf.getUserId());
			ps.setInt(2, uf.getBaseExchangeRateId());
			ps.setInt(3, uf.getTargetExchangeRateId());
			ps.setString(4, uf.getDateAdded().toString());
			
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				int newUserFavoriteId = rs.getInt(1);
				uf.setUserFavoriteId(newUserFavoriteId);
				return uf;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean deleteUserFavorite (int userFavoriteId) {
		String deleteSql = "DELETE FROM user_favorites WHERE user_favorite_id= ?";
		int ret = 0;
		
		try {
			PreparedStatement ps = this.connection.getConn().prepareStatement(deleteSql);
			ps.setInt(1, userFavoriteId);
			
			ret = ps.executeUpdate();	
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return (ret > 0);
	}
	
	public UserFavoriteModel parseUserFavoriteResult(ResultSet rs) {
		UserFavoriteModel uf = new UserFavoriteModel();
		try {
			uf.setUserFavoriteId(rs.getInt("user_favorite_id"));
			uf.setUserId(rs.getInt("user_id"));
			uf.setBaseExchangeRateId(rs.getInt("base_exchange_id"));
			uf.setTargetExchangeRateId(rs.getInt("target_exchange_id"));
			
			String dateString = rs.getString("date_added");
			DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate date = LocalDate.parse(dateString, inputFormatter);
			uf.setDateAdded(date);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return uf;
	}
}
