package Forex.App;

import java.time.LocalDate;
import java.util.ArrayList;
import Models.*;
import DAL.*;

public class ForexAppTest {
	
	// Database access class fields
	public static DataConnection dc;
	public static CurrencyDAL currencyDAL;
	public static ExchangeRateDAL exchangeRateDAL;
	public static UserFavoriteDAL userFavoriteDAL;
	public static UserDAL userDAL;
	public static ZipCodeDAL zipCodeDAL;
	
	// App State Fields
	public static UserModel loginUser;
	
	public static void main(String[] args) {
		// Setup DB connection
		DataConnection dc = new DataConnection();
		System.out.println("Created new connection to: " + dc.getDbFilePath());
		
		// Create DAL objects to connect to each table
		currencyDAL = new CurrencyDAL(dc);
		exchangeRateDAL = new ExchangeRateDAL(dc);
		userFavoriteDAL = new UserFavoriteDAL(dc);
		userDAL = new UserDAL(dc);
		zipCodeDAL = new ZipCodeDAL(dc);
		
		// Testing user registration/login/logout
		testUserRegistration();
		testUserLogin();
		testUserLogout();
		
		// ExchangeRate lookup and display
		testExchangeRateInput();
		
		// Close out DB connection
		dc.closeDataConnection();
	}
	
	public static void testUserRegistration() {
		System.out.println("\nTesting User Registration Ops:");
		
		// Create a new user - would get details from UI hooks
		UserModel u = new UserModel();
		ZipCodeModel z = new ZipCodeModel();
		u.setName("CJZ");
		u.setEmail("cjz3@test.com");
		u.setHashPassword("test");
		z.setZipCode(53562);
		z.setCity("Middleton");
		z.setState("Wisconsin");
		u.setZipCode(z);
		
		// Check that the new user does not exist
		UserModel outUser = null;
		if (userDAL.getUserByEmail(u.getEmail()) == null) {
			// Create the new zip and user
			zipCodeDAL.createZipCode(z);
			outUser = userDAL.createUser(u);
			// Set the new user as logged in
			loginUser = outUser;
			// Call other code here to update the UI to the logged in view
			System.out.println("- New user created and logged in: " + loginUser.getName() );
		} else {
			// Display some warning/UI element to tell the user this email is taken
			System.out.println("- Existing user found - new user not created.");
		}
	}
	
	public static void testUserLogin() {
		System.out.println("\nTesting User Login Ops:");
		
		// Check login details - would get from UI
		String loginEmail = "cjz3@test.com";
		String loginPass = "test";
		UserModel checkUser = userDAL.checkUserLogin(loginEmail, loginPass);
		if (checkUser != null) {
			// Set the found user as logged in
			loginUser = checkUser;
			// Call other code here to update UI to the logged in view
			System.out.println("- User logged in successfully: " + loginUser.getName() );
		} else {
			// Display some warning/UI element to tell the user the login failed
			System.out.println("- Unable to login, check email/pass: " 
					+ loginEmail + " / " + loginPass);
		}
	}
	
	public static void testUserLogout() {
		System.out.println("\nTesting User Logout Ops:");
		
		loginUser = null;
		// Switch UI back to the login/reg screen
		System.out.println("- Logout successful, goodbye!");
	}

	public static void testExchangeRateInput() {
		System.out.println("\nTesting Exchange Rate Input Ops:");
		// Get frequency from UI
		String frequency = "day";
		
		// Get base and target currencies (in UI we'd need to check if one or the other is already selected)
		CurrencyModel baseSymbol = null;
		CurrencyModel targetSymbol = null;
		int totalCurrency = 18;
		
		// Base - SQL code to return a list of all currencies available
		ArrayList<CurrencyModel> currencyList = currencyDAL.getCurrencyList();
		if (targetSymbol != null) {
			for (int i = 0; i < currencyList.size(); i++) {
				if (currencyList.get(i).getSymbolId() == targetSymbol.getSymbolId()) {
					currencyList.remove(i);
					i --;
				}
			}
		}
		baseSymbol = currencyList.get(0);
		if (currencyList.size() == totalCurrency) {
			System.out.println("- Found " + totalCurrency +" base currencies");
		} else { System.out.println("- Failed to find " + totalCurrency + " base currencies"); }
		
		// Target - SQL code to return all currencies except base
		currencyList = currencyDAL.getCurrencyList();
		if (baseSymbol != null) {
			for (int i = 0; i < currencyList.size(); i++) {
				if (currencyList.get(i).getSymbolId() == baseSymbol.getSymbolId()) {
					currencyList.remove(i);
					i --;
				}
			}
		}
		targetSymbol = currencyList.get(1);
		if (currencyList.size() == totalCurrency - 1) {
			System.out.println("- Found "+ (totalCurrency -1) +" target currencies");
		} else { System.out.println("- Failed to find "+ (totalCurrency -1) +" target currencies"); }
		
		// Get end date from start date (hard-coded at 7)
		// SQL code in exchange_rate table to group and return dates for base
		int numDates = 8;
		String freqStr = "week";
		LocalDate startDate = LocalDate.of(2019, 8, 5);
		ArrayList<LocalDate> dates = exchangeRateDAL.getNextDatesForSymbolId(
				startDate, baseSymbol.getSymbolId(), targetSymbol.getSymbolId(),
				freqStr, numDates);
		System.out.print("- Found " + numDates + " dates from "+ startDate + ": ");
		System.out.print(dates);
		System.out.println();
		
		testRetrieveExchangeRates(freqStr, dates, baseSymbol.getSymbolId(), 
				targetSymbol.getSymbolId());
	}
	
	public static void testRetrieveExchangeRates(String freq, ArrayList<LocalDate> dates,
			int baseSym, int tarSym) {
		System.out.println("\nTesting Exchange Rate Data Retrieval:");
		// Pass in input elements above... freq, date range, 2 symbol_ids
		
		// SQL query for base > ArrayList of exchange_rate rows
		ArrayList<ExchangeRateModel> baseExRates = exchangeRateDAL
				.getExchangeRatesOverDateRange(baseSym, freq, dates);
		System.out.println("- Found Base ExRates: " + baseExRates);
		
		// TODO - SQL query for target > ArrayList of exchange_rate rows
		ArrayList<ExchangeRateModel> targetExRates = exchangeRateDAL
				.getExchangeRatesOverDateRange(tarSym, freq, dates);
		System.out.println("- Found TargetExRates: " + targetExRates);
		
		// TODO - compute display element > inverse base x target for each field
		
	}
}
