package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import Models.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import DAL.*;


public class Main extends Application {

	public static DataConnection dc;
	public static CurrencyDAL currencyDAL;
	public static ExchangeRateDAL exchangeRateDAL;
	public static UserFavoriteDAL userFavoriteDAL;
	public static UserDAL userDAL;
	public static ZipCodeDAL zipCodeDAL;
	
	// Application State Variables
	public UserModel loggedInUser;
	public CurrencyModel bCurrency;
	public CurrencyModel tCurrency;
	public String inInterval;
	public LocalDate inStartDate; 
	public LocalDate inEndDate; 
	ArrayList<LocalDate> inDates;
	public ArrayList<ExchangeRateRow> tableRowsData = new ArrayList<ExchangeRateRow>();
	
	public Scene mainScene;
	public Scene regScene;
	public GridPane exchangeRateTable;
	
	@Override
	public void start(Stage primaryStage) {
        try {
        	//Create root holder
            BorderPane root = new BorderPane();
            
           //Call to create elements
            HBox toolBarHBox = createToolBarHBox();               
            
            //Create exchange rate table
            exchangeRateTable = createExchangeRateTable();
     
            //Set element placements in root
            root.setTop(toolBarHBox);
            root.setCenter(exchangeRateTable);
           
            // Main scene display settings/staging
            Color scenePaint = new Color(.99, .234, .234, .76);
            mainScene = new Scene(root, 800, 500);
            mainScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            mainScene.setFill(scenePaint);
            
            // Login and Register Scenes
            Scene loginScene = createLoginScene(primaryStage);
            regScene = createRegisterScene(primaryStage);

            // Set initial login Scene onto Stage
            primaryStage.setScene(loginScene);
            primaryStage.setTitle("ForexApp");
            primaryStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	private Scene createLoginScene(Stage primaryStage) {
		VBox loginRoot = new VBox();
        Scene loginScene = new Scene(loginRoot, 400, 400);
        
        // Email Label + Field
        Label emailLabel = new Label("Email:");
        TextField emailField= new TextField();
        HBox emailBox = new HBox(emailLabel, emailField);
        emailBox.setSpacing( 10.0d );
        emailBox.setAlignment(Pos.CENTER );
        emailBox.setPadding( new Insets(20) );
        
        // Password Label + Field
        Label passwordLabel = new Label("Password:");
        TextField passwordField= new PasswordField();
        HBox passwordBox = new HBox(passwordLabel, passwordField);
        passwordBox.setSpacing( 10.0d );
        passwordBox.setAlignment(Pos.CENTER );
        passwordBox.setPadding( new Insets(20) );
        
        // Login / Reg buttons
        Button loginButton = new Button("Login");        
        loginButton.setOnAction(event -> {
        	System.out.println("Login!");
        	String userEmail = emailField.getText();
        	String userPassword = passwordField.getText();
        	
        	UserModel checkUser = userDAL.checkUserLogin(userEmail, userPassword);
        	if (checkUser != null) {
    			// Set the found user as logged in
    			loggedInUser = checkUser;
    			// Call other code here to update UI to the logged in view
    			System.out.println("- User logged in successfully: " + checkUser.getName() );
    			primaryStage.setScene(mainScene);
    		} else {
    			// Display some warning/UI element to tell the user the login failed
    			System.out.println("- Unable to login, check email/pass: " 
    					+ userEmail + " / " + userPassword);
    			emailField.setText("");
    			passwordField.setText("");
    		}
        	event.consume();
        });
        Button regButton = new Button("New User?");        
        regButton.setOnAction(event -> {
        	System.out.println("Register");
        	primaryStage.setScene(regScene);
        	event.consume();
        });
        HBox loginButtonBox = new HBox(loginButton, regButton);
        loginButtonBox.setSpacing( 10.0d );
        loginButtonBox.setAlignment(Pos.CENTER );
        loginButtonBox.setPadding( new Insets(20) );
        
        loginRoot.getChildren().add(emailBox);
        loginRoot.getChildren().add(passwordBox);
        loginRoot.getChildren().add(loginButtonBox);
        
        
        return loginScene;
	}
	
	private Scene createRegisterScene(Stage primaryStage) {
		VBox regRoot = new VBox();
        Scene regScene = new Scene(regRoot, 400, 600);
        
        // Email Label + Field
        Label nameLabel = new Label("Name:");
        TextField nameField= new TextField();
        HBox nameBox = new HBox(nameLabel, nameField);
        nameBox.setSpacing( 10.0d );
        nameBox.setAlignment(Pos.CENTER );
        nameBox.setPadding( new Insets(20) );
        regRoot.getChildren().add(nameBox);
        
        // Email Label + Field
        Label emailLabel = new Label("Email:");
        TextField emailField= new TextField();
        HBox emailBox = new HBox(emailLabel, emailField);
        emailBox.setSpacing( 10.0d );
        emailBox.setAlignment(Pos.CENTER );
        emailBox.setPadding( new Insets(20) );
        regRoot.getChildren().add(emailBox);
        
        // Password Label + Field
        Label passwordLabel = new Label("Password:");
        TextField passwordField= new PasswordField();
        HBox passwordBox = new HBox(passwordLabel, passwordField);
        passwordBox.setSpacing( 10.0d );
        passwordBox.setAlignment(Pos.CENTER );
        passwordBox.setPadding( new Insets(20) );
        regRoot.getChildren().add(passwordBox);
        
        // Zip Label + Field
        Label zipLabel = new Label("Zip Code:");
        TextField zipField= new TextField();
        HBox zipBox = new HBox(zipLabel, zipField);
        zipBox.setSpacing( 10.0d );
        zipBox.setAlignment(Pos.CENTER );
        zipBox.setPadding( new Insets(20) );
        regRoot.getChildren().add(zipBox);
        
        // City Label + Field
        Label cityLabel = new Label("City:");
        TextField cityField= new TextField();
        HBox cityBox = new HBox(cityLabel, cityField);
        cityBox.setSpacing( 10.0d );
        cityBox.setAlignment(Pos.CENTER );
        cityBox.setPadding( new Insets(20) );
        regRoot.getChildren().add(cityBox);
        
        // State Label + Field
        Label stateLabel = new Label("State:");
        TextField stateField= new TextField();
        HBox stateBox = new HBox(stateLabel, stateField);
        stateBox.setSpacing( 10.0d );
        stateBox.setAlignment(Pos.CENTER );
        stateBox.setPadding( new Insets(20) );
        regRoot.getChildren().add(stateBox);
        
        // Create User button
        Button createUserButton = new Button("Create User and Login");
        HBox createUserBox= new HBox(createUserButton);
        createUserBox.setSpacing( 10.0d );
        createUserBox.setAlignment(Pos.CENTER );
        createUserBox.setPadding( new Insets(20) );
        regRoot.getChildren().add(createUserBox);
        
        createUserButton.setOnAction(event -> {
        	System.out.println("Reg user!");
        	
        	// Create User object
        	UserModel u = new UserModel();
        	u.setName(nameField.getText());
        	u.setEmail(emailField.getText());
        	u.setHashPassword(passwordField.getText());

        	// Create ZipCode object
        	ZipCodeModel z = new ZipCodeModel();
        	z.setZipCode(Integer.parseInt(zipField.getText()));
    		z.setCity(cityField.getText());
    		z.setState(stateField.getText());
    		u.setZipCode(z);
        	
    		// Check if the User exists - if not, create
    		UserModel outUser = null;
    		UserModel existingUser = userDAL.getUserByEmail(emailField.getText());
        	if (existingUser == null) {
    			// Create the new zip and user
        		zipCodeDAL.createZipCode(z);
        		outUser = userDAL.createUser(u);

        		// Set the new user as logged in
    			loggedInUser = outUser;
    			
    			// Call other code here to update the UI to the logged in view
    			System.out.println("- New user created and logged in: " 
    					+ loggedInUser.getName() );
    			primaryStage.setScene(mainScene);
    		} else {
    			// Display some warning/UI element to tell the user the login failed
    			System.out.println("- Unable to login, check email/pass: " 
    					+ emailField.getText() + " / " + passwordField.getText());
    			nameField.setText("");
    			emailField.setText("");
    			passwordField.setText("");
    			zipField.setText("");
    			cityField.setText("");
    			stateField.setText("");
    		}
        	event.consume();
        });
        
        return regScene;
	}
	
    private HBox createToolBarHBox() {
     
    	//Create Topbar Section Vboxes
    	
    	VBox toolBarVBoxLeft = createToolBarVBoxLeft();
    	VBox toolBarVBoxCenter = createToolBarVBoxCenter();
    	VBox toolBarVBoxRight = createToolBarVBoxRight();
    	
    	//Create parent HBox for TopBar with VBox sections
    	HBox toolBarHBox = new HBox(toolBarVBoxLeft, toolBarVBoxCenter, toolBarVBoxRight);
    	

    	// Bind the VBoxes' widths to the Toolbar width to make them proportional
    	toolBarVBoxLeft.prefWidthProperty().bind(toolBarHBox.widthProperty().divide(3));
    	toolBarVBoxCenter.prefWidthProperty().bind(toolBarHBox.widthProperty().divide(3));
    	toolBarVBoxRight.prefWidthProperty().bind(toolBarHBox.widthProperty().divide(3));
    	

        return toolBarHBox;
    }
    
    private VBox createToolBarVBoxLeft() {
    	
    	VBox toolBarVBoxLeft = new VBox();
    	
    	
    	//Base Currency Selection
    	Label baseCurrencyLabel = new Label("Base Currency:");
    	ChoiceBox<CurrencyModel> baseCurrChoice = new ChoiceBox<>();

    	// Get list of base currencies
    	List<CurrencyModel> currencyChoices = currencyDAL.getCurrencyList();
    	
    	// Override the base Currency String Methods - to control text in dropdown
    	baseCurrChoice.setConverter(new StringConverter<CurrencyModel>() {
    		@Override
    		public String toString(CurrencyModel c) {
    			if (c == null) {
    				return "";
    			}
    			return c.getSymbol();
    		}
    		@Override
            public CurrencyModel fromString(String string) {
                return null;
            }
    	});
    	
    	// Put base currency and label in an HBox to line up
    	baseCurrChoice.getItems().addAll(currencyChoices);
    	HBox bCurrHBox = new HBox(baseCurrencyLabel, baseCurrChoice);
    	bCurrHBox.setSpacing( 10.0d );
    	bCurrHBox.setAlignment(Pos.CENTER );
    	bCurrHBox.setPadding( new Insets(2) );
    	baseCurrChoice.setOnAction(event -> bCurrency = baseCurrChoice.getValue());
    	
    	// Target Currency Selection
    	Label targetCurrencyLabel = new Label("Target Currency:");
    	ChoiceBox<CurrencyModel> targetCurrChoice = new ChoiceBox<>();
    	targetCurrChoice.getItems().addAll(currencyChoices);
    	targetCurrChoice.setOnAction(event -> tCurrency = targetCurrChoice.getValue());
    	
    	// Put target currency and label in an HBox to line up
    	HBox tCurrHBox = new HBox(targetCurrencyLabel, targetCurrChoice);
    	tCurrHBox.setSpacing( 10.0d );
    	tCurrHBox.setAlignment(Pos.CENTER );
    	tCurrHBox.setPadding( new Insets(2) );
    	
        
        //Add Left section GUI elements as Vbox children
        toolBarVBoxLeft.getChildren().add(bCurrHBox);
        toolBarVBoxLeft.getChildren().add(tCurrHBox);
        
    	return toolBarVBoxLeft;
    }
    
    private VBox createToolBarVBoxCenter() {
    	
    	VBox toolBarVBoxCenter = new VBox();
    	
    	// Interval Selection 
    	Label intervalLabel = new Label("Interval:");
    	ObservableList <String> intervalOptions = 
    			FXCollections.observableArrayList("day", "week", "month");
    	ChoiceBox<String> intervalChoice = new ChoiceBox<String>(intervalOptions);
    	
    	HBox intervalBox = new HBox(intervalLabel, intervalChoice);
    	intervalBox.setSpacing( 10.0d );
    	intervalBox.setAlignment(Pos.CENTER );
    	intervalBox.setPadding( new Insets(2) );
    	toolBarVBoxCenter.getChildren().add(intervalBox);
    	
    	
    	// End Date Selection
    	Label endLabel = new Label("End Date:");
    	DatePicker endField = new DatePicker();
    	endField.setDisable(true);
    	HBox endBox = new HBox(endLabel, endField);
    	endBox.setSpacing( 10.0d );
    	endBox.setAlignment(Pos.CENTER );
    	endBox.setPadding( new Insets(2) );
    	
    	
    	// Start Date Selection
    	Label startLabel = new Label("Start Date:");
    	DatePicker startField = new DatePicker();
    	LocalDate initialDate = LocalDate.of(2019, 6, 30);
    	startField.setValue(initialDate);
    	startField.setDayCellFactory(field -> new DateCell() {
    		public void updateItem(LocalDate date, boolean empty) {
    			super.updateItem(date, empty);
    			LocalDate firstDate = LocalDate.of(2019, 6, 30);
    			LocalDate lastDate = LocalDate.of(2023, 7, 6);
    			boolean inRange = date.compareTo(lastDate) < 0 &&
    					date.compareTo(firstDate) > 0;

    			setDisable(empty || !inRange);
    		}
    	});
    	HBox startBox = new HBox(startLabel, startField);
    	startBox.setSpacing( 10.0d );
    	startBox.setAlignment(Pos.CENTER );
    	startBox.setPadding( new Insets(2) );
    	
    	toolBarVBoxCenter.getChildren().add(startBox);
    	toolBarVBoxCenter.getChildren().add(endBox);
    	
    	// Event Handlers
    	// Interval Events
    	intervalChoice.setOnAction(event -> {
    		inInterval = intervalChoice.getValue();
    		startField.setValue(initialDate);
    		inStartDate = initialDate;
    		endField.setValue(null);
    		inEndDate = null;
    	});
    	
    	startField.setOnAction(e -> {
    		if (bCurrency == null || tCurrency == null || inStartDate == null
    				|| inInterval.isBlank()) {
    			return;
    		}
    		
    		System.out.println(startField.getValue());
    		inStartDate = startField.getValue();
    		
    		// Get valid end date based on start date:
    		ArrayList<LocalDate> dates = exchangeRateDAL.getNextDatesForSymbolId(
    				inStartDate, bCurrency.getSymbolId(), tCurrency.getSymbolId(),
    				inInterval, 7);
    		
    		inDates = dates;
    		
    		endField.setValue(dates.get(dates.size()-1));
    		inEndDate = endField.getValue();
    	});
    	
    	
    	return toolBarVBoxCenter;
    }
    
    private VBox createToolBarVBoxRight() {
    	
    	VBox toolBarVBoxRight = new VBox();
    	
    	
    	// Create button elements 
        Button favoriteButton = new Button("Favorite");
        
        Button addButton = new Button("➕ Add Exchange Rate");
        addButton.setOnAction(event -> {
        	System.out.println("Add currency exchange!");
//        	exchangeRateTable.getChildren().clear();
        	updateExchangeRateTable(exchangeRateTable);
        });

    	//Set elements as children of VBox
    	
        toolBarVBoxRight.getChildren().add(favoriteButton);
        toolBarVBoxRight.getChildren().add(addButton);

    	return toolBarVBoxRight;
    }
    
    private void updateExchangeRateTable(GridPane table) {
    	// Insert new Exchange Rate
    	ArrayList<ExchangeRateModel> baseExRates = exchangeRateDAL
    			.getExchangeRatesOverDateRange(bCurrency.getSymbolId(), 
    					inInterval, inDates);
		System.out.println("- Found Base ExRates: " + baseExRates);
		
		ArrayList<ExchangeRateModel> targetExRates = exchangeRateDAL
				.getExchangeRatesOverDateRange(tCurrency.getSymbolId(), 
						inInterval, inDates);
		System.out.println("- Found TargetExRates: " + targetExRates);
		
		// Save the new row to our table data
		ExchangeRateRow newRow = new ExchangeRateRow(baseExRates, targetExRates);
		tableRowsData.add(newRow);
    	
    	// Clear Table
    	table.getChildren().clear();
    	// Add Header with dates
    	addTableDatesHeader(table);
    	
    	// Loop through the table data and add a row to the grid for each
    	int row = 1;
    	for (int i = 0; i < tableRowsData.size(); i++) {
    		ExchangeRateRow currentRow = tableRowsData.get(i);
    		ArrayList<ExchangeRateModel> calcValues = currentRow.calcValues;
    		
    		Label buttonLabel = new Label("Add butons here");
    		table.add(buttonLabel, 0, row);
    		
    		int col = 1;
    		for (int j = 0; j < calcValues.size(); j++) {
    			// Just do open for now...
    			double open = calcValues.get(j).getOpen();
    			Label dataLabel = new Label(Double.toString(open));
    			table.add(dataLabel, col, row);
    			col++;
    		}
    		
    		// Add right side delete button
    		Label deleteLabel = new Label("X");
    		table.add(deleteLabel, 8, row);
    		
    		row++;
        }
    }
    
    private void addTableDatesHeader(GridPane table) {
    	int dateCounter = 0;
    	ArrayList<LocalDate> dates = inDates;
    	for (int col = 1; col < 8; col++) {
        	Label label = new Label(dates.get(dateCounter).toString());
        	dateCounter++;
        	table.add(label, col, 0);
        }
    }
    
    private void addTableDatesEmptyHeader(GridPane table) {
    	// Create Empty exchange rate table
    	for (int col = 1; col < 8; col++) {
        	Label label = new Label("Date " + col);
        	table.add(label, col, 0);
        }
    }
    
    private GridPane createExchangeRateTable() {
        GridPane gridPane = new GridPane();
        
        // Add Empty Date Headers
        addTableDatesEmptyHeader(gridPane);
        
        // TODO - add existing user favorites instead of blanks
        
//        for (int row = 1; row < 6; row++) {
//            // 9 columns to account for button columns on left and right ends 
//        	for (int col = 0; col < 9; col++) {
//            	
//            	//TODO Get data from query to display here
//            	
//            	//Placeholder display
//        		String labelStr = "R" + (row + 1) + "C" + (col + 1);
//                Label label = new Label(labelStr);
//               
//                gridPane.add(label, col, row);
//            }
//        }

        // Set the vertical and horizontal gaps between cells
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        return gridPane;
    }

    //main routine
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
    	
    	
    	launch(args);
    }	    
}

	

