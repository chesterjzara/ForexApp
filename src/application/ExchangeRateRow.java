package application;

import java.time.LocalDate;
import java.util.ArrayList;

import Models.*;

public class ExchangeRateRow {
	public int id;
	public ArrayList<ExchangeRateModel> base;
	public ArrayList<ExchangeRateModel> target;
	public boolean isFavorite;
	public ArrayList<ExchangeRateModel> calcValues;
	
	public static int idCount = 0;
	
	public ExchangeRateRow() {};
	
	public ExchangeRateRow(ArrayList<ExchangeRateModel> base, ArrayList<ExchangeRateModel> target, boolean isFavorite) {
		this.base = base;
		this.target = target;
		this.isFavorite = isFavorite;
		
		this.calcValues = calculateValues(base, target);
	}
	
	public ExchangeRateRow(ArrayList<ExchangeRateModel> base, ArrayList<ExchangeRateModel> target) {
		this(base, target, false);
	}
	
	public ArrayList<ExchangeRateModel> calculateValues(ArrayList<ExchangeRateModel> base, ArrayList<ExchangeRateModel> target) {
		ArrayList<ExchangeRateModel> calc = new ArrayList<ExchangeRateModel>();
		
		// Iterate over each exchange rate in the base + target lists
		for (int i = 0; i < base.size(); i++) {
			// New Exchange rate for each date
			calc.add(new ExchangeRateModel()); 
			
			// Set date
			LocalDate date = base.get(i).getDate();
			calc.get(i).setDate(date);
			
			// Set each numeric value in the calc list of exchange rates
			double baseOpen = base.get(i).getOpen();
			double tarOpen = target.get(i).getOpen();
			double calcOpen = (1/baseOpen) * tarOpen;
			calc.get(i).setOpen(calcOpen);
			
			double baseClose = base.get(i).getClose();
			double tarClose = target.get(i).getClose();
			double calcClose = (1/baseClose) * tarClose;
			calc.get(i).setClose(calcClose);
			
			double baseHigh = base.get(i).getHigh();
			double tarHigh = target.get(i).getHigh();
			double calcHigh = (1/baseHigh) * tarHigh;
			calc.get(i).setHigh(calcHigh);
			
			double baseLow = base.get(i).getLow();
			double tarLow = target.get(i).getLow();
			double calcLow = (1/baseLow) * tarLow;
			calc.get(i).setLow(calcLow);
			
			double baseVolume = base.get(i).getVolume();
			double tarVolume = target.get(i).getVolume();
			double calcVolume = (1/baseVolume) * tarVolume;
			calc.get(i).setVolume(calcVolume);	
		}

		return calc;
	}
}
