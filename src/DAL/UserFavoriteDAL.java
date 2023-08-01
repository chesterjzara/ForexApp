package DAL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Models.*;

public class UserFavoriteDAL {
	private DataConnection connection;
	
	public UserFavoriteDAL(DataConnection connection) {
        this.connection = connection;
    }
	
	public ArrayList<UserFavoriteModel> getUserFavoritesByUserId(int userId) {
		String sql = "SELECT * FROM user_favorites uf WHERE uf.userId= " + userId;
		ArrayList<UserFavoriteModel> userFavoriteList = new ArrayList<UserFavoriteModel>();
		try {
			ResultSet rs = connection.getConn().createStatement()
					.executeQuery(sql);
			rs.next();
			
			while(rs.next()) {
				
			}
			if (rs.getInt("id") > 0) {
								
				UserFavoriteModel uf = parseUserFavoriteResult(rs);
				userFavoriteList.add(uf);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userFavoriteList;
	}
	
	public UserFavoriteModel parseUserFavoriteResult(ResultSet rs) {
		UserFavoriteModel uf = new UserFavoriteModel();
		try {
			uf.setId(rs.getInt("id"));
			uf.setFavoriteId(rs.getInt("favoriteId"));
			uf.setUserId(rs.getInt("userId"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return uf;
	}
}
