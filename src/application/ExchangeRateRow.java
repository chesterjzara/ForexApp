package application;

import java.time.LocalDate;
import java.util.ArrayList;

import Models.*;

public class ExchangeRateRow {
	public int id;
	public ArrayList<ExchangeRateModel> base;
//	public CurrencyModel baseCurrency;
	public ArrayList<ExchangeRateModel> target;
//	public CurrencyModel targetCurrency;
	public boolean isFavorite;
	public int userFavoriteId;
	public ArrayList<ExchangeRateModel> calcValues;
	
	public static int idCount = 0;
	
	public ExchangeRateRow() {};
	
	public ExchangeRateRow(ArrayList<ExchangeRateModel> base, ArrayList<ExchangeRateModel> target, boolean isFavorite) {
		this.base = base;
		this.target = target;
		this.isFavorite = isFavorite;
		
		this.calcValues = calculateValues(base, target);
	}
	
//	public ExchangeRateRow(ArrayList<ExchangeRateModel> base, CurrencyModel baseCurrency, 
//			ArrayList<ExchangeRateModel> target, CurrencyModel targetCurrency, boolean isFavorite) {
//		this.base = base;
//		this.baseCurrency = baseCurrency;
//		this.target = target;
//		this.targetCurrency = targetCurrency;
//		this.isFavorite = isFavorite;
//		
//		this.calcValues = calculateValues(base, target);
//	}
	
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
	
	public ExchangeRateModel calculateAvgValues() {
		ArrayList<ExchangeRateModel> values = calculateValues(base, target);
		ExchangeRateModel avgCalc = new ExchangeRateModel();
		
		double totalOpen = 0;
		double totalClose = 0;
		double totalHigh = 0;
		double totalLow = 0;
		double totalVolume = 0;
		
		for (int i = 0; i < values.size(); i++) {
			totalOpen += values.get(i).getOpen();
			totalClose += values.get(i).getClose();
			totalHigh += values.get(i).getHigh();
			totalLow += values.get(i).getLow();
			totalVolume += values.get(i).getVolume();
		}
		
		avgCalc.setOpen(totalOpen/values.size());
		avgCalc.setClose(totalClose/values.size());
		avgCalc.setHigh(totalHigh/values.size());
		avgCalc.setLow(totalLow/values.size());
		avgCalc.setVolume(totalVolume/values.size());
		
		return avgCalc;
	}
	
	public int getBaseExRateId() {
		return this.base.get(0).getExchangeId();
	}
	
	public int getTargetExRateId() {
		return this.target.get(0).getExchangeId();
	}
	
	public CurrencyModel getBaseCurrency() {
		return this.base.get(0).getCurrency();
	}
	
	public int getBaseCurrencyId() {
		return this.base.get(0).getSymbolId();
	}

	public CurrencyModel getTarCurrency() {
		return this.target.get(0).getCurrency();
	}
	
	public int getTarCurrencyId() {
		return this.target.get(0).getSymbolId();
	}
	
	public String exchangeRateLabel() {
		String baseName = this.base.get(0).getCurrency().getSymbol();
		String targetName = this.target.get(0).getCurrency().getSymbol();
		return baseName + " / " + targetName;
	}
}
