package Forex.App;

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
		
		// Testing user registration ops
		testUserRegistration();
		
		// Testing user login operations
		testUserLogin();
		
		testUserLogout();
		
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
}
