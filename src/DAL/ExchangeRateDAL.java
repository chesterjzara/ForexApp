package DAL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

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
	
	public static final String exchangeRatesOverDateRange = 
			"SELECT * FROM exchange_rate\r\n"
			+ "WHERE date >= ? AND symbol_id = ? AND frequency = ?\r\n"
			+ "LIMIT ?";
	
	public ArrayList<ExchangeRateModel> getExchangeRatesOverDateRange(int symbolId,
			String freq, ArrayList<LocalDate> dates) {
		ArrayList<ExchangeRateModel> ret = new ArrayList<ExchangeRateModel>();
		
		try {
			PreparedStatement p = this.connection.getConn()
					.prepareStatement(exchangeRatesOverDateRange);
			String dateStr = dates.get(0).toString();
			p.setString(1, dateStr);
			p.setInt(2, symbolId);
			p.setString(3, freq);
			p.setInt(4, dates.size());
			ResultSet rs = p.executeQuery();
			
			while (rs.next()) {
				ExchangeRateModel e = parseExchangeRateResult(rs);
				ret.add(e);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		if (ret.size() > 0) {
			return ret; 
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
//			java.sql.Date sqlDate = new java.sql.Date(e.getDate().getTime());
//			p.setDate(1, sqlDate);
			p.setString(1, e.getDate().toString());
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
			
			String dateString = rs.getString("date");
			DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate date = LocalDate.parse(dateString, inputFormatter);
			e.setDate(date);
			
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
	
	// 1 start date, 2 base sym, 3 tar sym 
	public static final String exchangeRateDateRange = 
			"WITH check_symbol as (\n"
			+ "    SELECT date, symbol_id\n"
			+ "    FROM exchange_rate e\n"
			+ "    WHERE e.date >= ? AND (symbol_id = ? or symbol_id = ?) AND frequency = ? \n"
			+ "    GROUP BY date, symbol_id \n"
			+ "    ORDER BY date \n"
			+ "),\n"
			+ "check_count as (\n"
			+ "    SELECT date, count(symbol_id) as count\n"
			+ "    FROM check_symbol\n"
			+ "    GROUP BY date\n"
			+ ")\n"
			+ "SELECT date FROM check_count WHERE count = 2 LIMIT ?;";
	
	public ArrayList<LocalDate> getNextDatesForSymbolId(LocalDate date, int baseSym, 
			int tarSym, String frequency, int numDays) {
		ArrayList<LocalDate> retDateList = new ArrayList<LocalDate>();
		
		try {
			PreparedStatement p = this.connection.getConn()
					.prepareStatement(exchangeRateDateRange);
			String dateStr = date.toString();
			p.setString(1, dateStr);
			p.setInt(2, baseSym);
			p.setInt(3, tarSym);
			p.setString(4, frequency);
			p.setInt(5, numDays);
			ResultSet rs = p.executeQuery();
			
			while (rs.next()) {
				String dateString = rs.getString("date");
				DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate localDate = LocalDate.parse(dateString, inputFormatter);
				retDateList.add(localDate);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		if (retDateList.size() > 0) {
			return retDateList; 
		}
		return null;
	}
}
