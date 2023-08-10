// TODO - delete - probably don't need - marked deprecated

package DAL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Models.*;

@Deprecated 
public class FavoriteDAL {
	private DataConnection connection;
	
	public FavoriteDAL(DataConnection connection) {
        this.connection = connection;
    }
	
	public FavoriteModel getFavoriteById(int id) {
		String sql = "SELECT * FROM favorite f WHERE f.id=" + id;
		
		try {
			ResultSet rs = connection.getConn().createStatement()
					.executeQuery(sql);
			rs.next();
			
			if (rs.getInt("id") > 0) {
//				int fId = rs.getInt("id");
//				int baseExchId = rs.getInt("baseExchId");
//				int tarExchId = rs.getInt("tarExchId");
//				
//				FavoriteModel test = new FavoriteModel(fId, baseExchId, tarExchId);
//				return test;
				return parseFavoriteResult(rs);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
//	public ArrayList<FavoriteModel> getFavoritesByUser(int userId) {
//		String sql = "SELECT * FROM favorite f WHERE f.userId=" + userId;
//		ArrayList<FavoriteModel> favoriteList = new ArrayList<FavoriteModel>();
//		
//		try {
//			ResultSet rs = connection.getConn().createStatement()
//					.executeQuery(sql);
//			while(rs.next()) {
//				FavoriteModel newFav = parseFavoriteResult(rs);
//				favoriteList.add(newFav);
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return favoriteList;
//	}
	
	public boolean createFavorite(FavoriteModel favInput) {
		int ret = 0;
		String insertSql = "INSERT INTO favorite VALUES (?, ?, ?);";
		
		try {
			PreparedStatement pstmt = this.connection.getConn()
					.prepareStatement(insertSql);
			pstmt.setInt(1, favInput.getId());
			pstmt.setInt(2, favInput.getBaseExchId());
			pstmt.setInt(3, favInput.getTarExchId());
			
			ret = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return (ret > 0);
	}
	
	public FavoriteModel updateFavorite(FavoriteModel favInput) {
		//TODO
		return null;
	}
	
	public boolean deleteFavorite(int id) {
		// TODO
		return false;
	}
	
	private FavoriteModel parseFavoriteResult(ResultSet rs) {
		FavoriteModel f = new FavoriteModel();
		try {
			f.setId(rs.getInt("id"));
			f.setBaseExchId(rs.getInt("baseExchId"));
			f.setTarExchId(rs.getInt("tarExchId"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return f;
	}
}
