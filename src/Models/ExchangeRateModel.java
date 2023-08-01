package Models;

import java.sql.Date;

public class ExchangeRateModel {
	private int id;
	private Date date;
	private int interval;
	private int currencyId;
	private double open;
	private double close;
	private double high;
	private double low;
	private double volume;
	
	public ExchangeRateModel(int id, Date date, int interval, int currencyId, 
			double open, double close, double high, double low, double volume) {
		super();
		this.id = id;
		this.date = date;
		this.interval = interval;
		this.currencyId = currencyId;
		this.open = open;
		this.close = close;
		this.high = high;
		this.low = low;
		this.volume = volume;
	}

	public ExchangeRateModel() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public int getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(int currencyId) {
		this.currencyId = currencyId;
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
		return "" + id + " on " + date + " by " + interval; 
	}
}
