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
	
	public static final String userFavAndCurrencyInfo = 
			"SELECT uf.*, cb.symbol 'base_symbol', ct.symbol 'tar_symbol', eb.date 'start_date'\r\n"
			+ "FROM user_favorites uf\r\n"
			+ "JOIN exchange_rate eb on uf.base_exchange_id = eb.exchange_id\r\n"
			+ "JOIN currency cb on eb.symbol_id = cb.symbol_id\r\n"
			+ "JOIN exchange_rate et on uf.target_exchange_id = et.exchange_id\r\n"
			+ "JOIN currency ct on et.symbol_id = ct.symbol_id\r\n"
			+ "WHERE user_id = ?;";
	
	public ArrayList<UserFavoriteModel> getUserFavoritesByUserId(int userId) {
		ArrayList<UserFavoriteModel> userFavoriteList = new ArrayList<UserFavoriteModel>();
		try {
			PreparedStatement p = this.connection.getConn().prepareStatement(userFavAndCurrencyInfo);
			p.setInt(1, userId);
			ResultSet rs = p.executeQuery();
			
			while(rs.next()) {
				if (rs.getInt("user_favorite_id") > 0) {
					UserFavoriteModel uf = parseUserFavoriteResult(rs);

					CurrencyModel bc = new CurrencyModel();
					bc.setSymbol(rs.getString("base_symbol"));
					ExchangeRateModel be = new ExchangeRateModel();
					be.setCurrency(bc);
					
					String dateString = rs.getString("start_date");
					DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					LocalDate date = LocalDate.parse(dateString, inputFormatter);
					be.setDate(date);
					
					uf.setBaseExchangeRate(be);
					
					CurrencyModel tc = new CurrencyModel();
					tc.setSymbol(rs.getString("tar_symbol"));
					ExchangeRateModel te = new ExchangeRateModel();
					te.setCurrency(tc);
					uf.setTargetExchangeRate(te);
					
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
	
	public boolean deleteUserFavoriteSearch (int userId, int baseId, int tarId) {
		String deleteSql = "DELETE FROM user_favorites WHERE user_id= ? AND base_exchange_id = ? AND target_exchange_id = ?";
		int ret = 0;
		
		try {
			PreparedStatement ps = this.connection.getConn().prepareStatement(deleteSql);
			ps.setInt(1, userId);
			ps.setInt(2, baseId);
			ps.setInt(3, tarId);
			
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
