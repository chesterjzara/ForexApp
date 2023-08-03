package Forex.App;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import com.opencsv.CSVReader;

import DAL.*;
import Models.*;

public class ForexAppSetup {

	public static DataConnection dc;
	public static CurrencyDAL currencyDAL;
	public static ExchangeRateDAL exchangeRateDAL;
	
	public static void main(String[] args) {
		dc = new DataConnection();
		System.out.println("Created new connection to: " + dc.getDbFilePath() + "\n");
		
		System.out.println("Checking and creating tables if they are missing...");
		DataConnection.checkCreateTables(dc.getConn());
		
		// Create DAL objects to update records
		currencyDAL = new CurrencyDAL(dc);
		exchangeRateDAL = new ExchangeRateDAL(dc);
		
		// Parse in the CSV file data
		handleCsvFiles();
	}
	
	public static void handleCsvFiles() {
		String dirPath = "CurrencyDataClean/";
		ArrayList<File> currencyFiles = ListCSVFiles(dirPath);
		ArrayList<File> dayExRateFiles = ListCSVFiles(dirPath + "day/");
		ArrayList<File> weekExRateFiles = ListCSVFiles(dirPath + "week/");
		ArrayList<File> monthExRateFiles = ListCSVFiles(dirPath + "month/");
        
		for (File csvFile : currencyFiles) {
            loadCurrencyDataFromCSV(csvFile);
        }
		
		for (File csvFile : dayExRateFiles) {
			loadExRateDataFromCSV(csvFile, "day");
        }
		
		for (File csvFile : weekExRateFiles) {
			loadExRateDataFromCSV(csvFile, "week");
        }
		
		for (File csvFile : monthExRateFiles) {
			loadExRateDataFromCSV(csvFile, "month");
        }
	}
	
	private static ArrayList<File> ListCSVFiles(String dirPath) {
		ArrayList<File> csvFilesList = new ArrayList<>();
		File dir = new File(dirPath);
		
		if (dir.exists() && dir.isDirectory()) {
			File[] files = dir.listFiles();
			if (files != null) {
				for (File file : files) {
                    if (file.isFile() && file.getName().toLowerCase().endsWith(".csv")) {
                        csvFilesList.add(file);
                    }
                }
			}
		}
		return csvFilesList;
	}
	
	private static void loadCurrencyDataFromCSV(File csvFile) {
		try {
			// Setup CSV reader
			CSVReader reader = new CSVReader(new BufferedReader(new FileReader(csvFile)));
			String[] nextLine;
			
			// Parse each line of the CSV into a CurrencyModel object
			while((nextLine = reader.readNext()) != null) {
				CurrencyModel c = new CurrencyModel();
				c.setSymbol(nextLine[0]);
				c.setCountry(nextLine[1]);
				c.setPopulation(Double.parseDouble(nextLine[2]));
				c.setGdp(Double.parseDouble(nextLine[3])); 
				c.setDebt(Double.parseDouble(nextLine[4]));
				c.setBirthRate(Double.parseDouble(nextLine[5]));
				c.setAverageAge(Double.parseDouble(nextLine[6]));
				c.setBillionaires(Integer.parseInt(nextLine[7]));
				c.setLandArea(Integer.parseInt(nextLine[8]));
				c.setDensity(Double.parseDouble(nextLine[9]));
				c.setLandlocked(Boolean.parseBoolean(nextLine[10]));
				c.setGoldReserves(Double.parseDouble(nextLine[11]));
				c.setMilesHighway(Double.parseDouble(nextLine[12]));
				c.setMaleObesity(Double.parseDouble(nextLine[13]));
				c.setFemaleObesity(Double.parseDouble(nextLine[14]));
				
				// Save the CurrencyModel obj to the DB!
				currencyDAL.createCurrency(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void loadExRateDataFromCSV(File csvFile, String freq) {
		try {
			// Setup CSV reader
			CSVReader reader = new CSVReader(new BufferedReader(new FileReader(csvFile)));
			String[] nextLine;
			
			// Skip one row to avoid header
			reader.readNext();
			
			DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			// Parse each line of the CSV into a CurrencyModel object
			while((nextLine = reader.readNext()) != null) {
				ExchangeRateModel e = new ExchangeRateModel();
				e.setExchangeId(0);
				
				// Take YYYY-MM-DD format turn into LocalDate
				LocalDate date = LocalDate.parse(nextLine[0], inputFormatter);
				e.setDate(date);
				
				// Lookup symbol_id in currency table from symbol
				String symbol = nextLine[1];
				int symbolId = currencyDAL.getCurrencyIdFromSymbol(symbol);
				e.setSymbolId(symbolId); // Need to find ID based on symbol via DB check
				
				e.setFrequency(freq);
				
				e.setOpen(Double.parseDouble(nextLine[3]));
				e.setHigh(Double.parseDouble(nextLine[4]));
				e.setLow(Double.parseDouble(nextLine[5]));
				e.setClose(Double.parseDouble(nextLine[6]));
				e.setVolume(Double.parseDouble(nextLine[7]));
				
				// Save the CurrencyModel obj to the DB!
				exchangeRateDAL.createExchangeRate(e);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
}
