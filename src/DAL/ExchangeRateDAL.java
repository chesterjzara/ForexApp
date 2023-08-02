package DAL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Models.*;

public class ExchangeRateDAL {
	private DataConnection connection;
	
	public static final String exchangeRateTableDLL = 
			"CREATE TABLE exchange_rate ("
					+ "exchange_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE,"
					+ "date        TEXT,"
					+ "symbol_id   INTEGER REFERENCES currency (symbol_id),"
					+ "frequency   TEXT,"
					+ "open        NUMERIC,"
					+ "close       NUMERIC,"
					+ "high        NUMERIC,"
					+ "low         NUMERIC,"
					+ "volume      NUMERIC);";		
	
	public ExchangeRateDAL(DataConnection connection) {
        this.connection = connection;
    }
	
	public ExchangeRateModel getExchangeRateById(int id) {
		String sql = "SELECT * FROM exchange e WHERE e.id=" + id;
		try {
			ResultSet rs = connection.getConn().createStatement()
					.executeQuery(sql);
			rs.next();
			
			if (rs.getInt("id") > 0) {
				ExchangeRateModel e = parseExchangeRateResult(rs);
				return e;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean createExchangeRate(ExchangeRateModel e) {
		// Null for first value to auto increment symbol_id
		String insertSql = "INSERT INTO exchange_rate "
				+ "VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?);";
		int ret = 0;
		
		try {
			PreparedStatement p = this.connection.getConn()
					.prepareStatement(insertSql);
			p.setDate(1, e.getDate());
			p.setInt(2, e.getSymbolId());
			p.setString(3, e.getFrequency());
			p.setDouble(4, e.getOpen());
			p.setDouble(5, e.getClose());
			p.setDouble(6, e.getHigh());
			p.setDouble(7, e.getLow());
			p.setDouble(8, e.getVolume());
			
			ret = p.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return (ret>0);
	}
	
	private ExchangeRateModel parseExchangeRateResult(ResultSet rs) {
		ExchangeRateModel e = new ExchangeRateModel();
		try {
			e.setExchangeId(rs.getInt("exchange_id"));
			e.setDate(rs.getDate("date"));
			e.setSymbolId(rs.getInt("symbol_id"));
			e.setFrequency(rs.getString("frequency"));
			e.setOpen(rs.getDouble("open"));
			e.setClose(rs.getDouble("close"));
			e.setHigh(rs.getDouble("high"));
			e.setLow(rs.getDouble("low"));
			e.setVolume(rs.getDouble("volume"));
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		return e;
	}
	
}