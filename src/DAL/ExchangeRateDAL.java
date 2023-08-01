package DAL;

import java.sql.ResultSet;
import java.sql.SQLException;

import Models.*;

public class ExchangeRateDAL {
	private DataConnection connection;
	
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
	
	private ExchangeRateModel parseExchangeRateResult(ResultSet rs) {
		ExchangeRateModel e = new ExchangeRateModel();
		try {
			e.setId(rs.getInt("id"));
			e.setDate(rs.getDate("date"));
			e.setInterval(rs.getInt("interval"));
			e.setCurrencyId(rs.getInt("currencyId"));
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
