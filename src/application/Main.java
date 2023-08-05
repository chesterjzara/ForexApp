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
	
	public Scene mainScene;
	public Scene regScene;
	
	@Override
	public void start(Stage primaryStage) {
        try {
        	//Create root holder
            BorderPane root = new BorderPane();
            
           //Call to create elements
            HBox toolBarHBox = createToolBarHBox();               
            
            //Create exchange rate table
            GridPane exchangeRateTable = createExchangeRateTable();
     
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
    	
    	// Target Currency Selection
    	Label targetCurrencyLabel = new Label("Target Currency:");
    	ChoiceBox<CurrencyModel> targetCurrChoice = new ChoiceBox<>();
    	targetCurrChoice.getItems().addAll(currencyChoices);
    	
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
    	
    	return toolBarVBoxCenter;
    }
    
    private VBox createToolBarVBoxRight() {
    	
    	VBox toolBarVBoxRight = new VBox();
    	
    	
    	// Create button elements 
        Button favoriteButton = new Button("Favorite");
       

    	//Set elements as children of VBox
    	
        toolBarVBoxRight.getChildren().add(favoriteButton);

    	return toolBarVBoxRight;
    }
    
    private GridPane createExchangeRateTable() {
        GridPane gridPane = new GridPane();

        // Create exchange rate table
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
            	
            	//TODO Get data from query to display here
            	
            	//Placeholder display
                Label label = new Label("R" + (row + 1) + "C" + (col + 1));
               
                gridPane.add(label, col, row);
            }
        }

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

	

