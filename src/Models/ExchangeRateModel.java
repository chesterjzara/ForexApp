package Models;

import java.sql.Date;

public class ExchangeRateModel {
	private int exchangeId;
	private Date date;
	private int symbolId;
	private String frequency;
	private double open;
	private double close;
	private double high;
	private double low;
	private double volume;
	
	public ExchangeRateModel(int id, Date date, String frequency, int symbolId, 
			double open, double close, double high, double low, double volume) {
		super();
		this.exchangeId = id;
		this.date = date;
		this.frequency = frequency;
		this.symbolId = symbolId;
		this.open = open;
		this.close = close;
		this.high = high;
		this.low = low;
		this.volume = volume;
	}

	public ExchangeRateModel() {}

	public int getExchangeId() {
		return exchangeId;
	}

	public void setExchangeId(int id) {
		this.exchangeId = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public int getSymbolId() {
		return symbolId;
	}

	public void setSymbolId(int symbolId) {
		this.symbolId = symbolId;
	}

	public double getOpen() {
		return open;
	}

	public void setOpen(double open) {
		this.open = open;
	}

	public double getClose() {
		return close;
	}

	public void setClose(double close) {
		this.close = close;
	}

	public double getHigh() {
		return high;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public double getLow() {
		return low;
	}

	public void setLow(double low) {
		this.low = low;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public String toString() {
		return "" + exchangeId + " on " + date + " by " + frequency; 
	}
}