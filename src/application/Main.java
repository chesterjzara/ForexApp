package application;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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
	public static FriendDAL friendDAL;
	
	
	// Application State Variables
	public UserModel loggedInUser;
	
	public CurrencyModel bCurrency;
	public ChoiceBox<CurrencyModel> fBCurrency;
	public CurrencyModel tCurrency;
	public ChoiceBox<CurrencyModel> fTCurrency;
	public String inInterval;
	public ChoiceBox<String> fInterval;
	public LocalDate inStartDate; 
	public DatePicker fStartDate;
	public LocalDate inEndDate; 
	public DatePicker fEndDate;
	public ArrayList<LocalDate> inDates;
	
	public ArrayList<ExchangeRateRow> tableRowsData = new ArrayList<ExchangeRateRow>();
	public int selectedRow;
	public ArrayList<UserFavoriteModel> favoriteList = new ArrayList<UserFavoriteModel>();
	public ArrayList<FriendTableRow> friendList = new ArrayList<FriendTableRow>();
	
	public DoubleProperty dOpen = new SimpleDoubleProperty(); 
	public DoubleProperty dClose = new SimpleDoubleProperty();
	public DoubleProperty dHigh = new SimpleDoubleProperty();
	public DoubleProperty dLow = new SimpleDoubleProperty();
	public DoubleProperty dVolume = new SimpleDoubleProperty();
	public StringProperty dCountry1 = new SimpleStringProperty();
	public StringProperty dCountry2 = new SimpleStringProperty();
	public DoubleProperty dGdp1 = new SimpleDoubleProperty();
	public DoubleProperty dGdp2 = new SimpleDoubleProperty();
	public DoubleProperty dDebt1 = new SimpleDoubleProperty();
	public DoubleProperty dDebt2 = new SimpleDoubleProperty();
	public DoubleProperty dDensity1 = new SimpleDoubleProperty();
	public DoubleProperty dDensity2 = new SimpleDoubleProperty();
	public IntegerProperty dLandArea1 = new SimpleIntegerProperty();
	public IntegerProperty dLandArea2 = new SimpleIntegerProperty();
	
	
	public Scene mainScene;
	public Scene regScene;
	public Scene loginScene;
	public Scene favoritesScene;
	public Scene friendsScene;
	
	public GridPane exchangeRateTable;
	public VBox detailPane;
	public TableView<UserFavoriteModel> favTable;
	public TableView<FriendTableRow> friendTable;

	// Constants
	public final int TABLE_DELETE_COL = 8;
	public final int TABLE_DATA_ROWS = 7;
	
	@Override
	public void start(Stage primaryStage) {
        try {  
            
        	// Create Login, Register, Main Scenes - save to global state to track
            loginScene = createLoginScene(primaryStage);
            regScene = createRegisterScene(primaryStage);
            mainScene = createMainScene(primaryStage);
            favoritesScene = createFavoritesScene(primaryStage);
            friendsScene = createFriendsScene(primaryStage);

            // Set initial login Scene onto Stage
            primaryStage.setScene(loginScene);
            primaryStage.setTitle("ForexApp");
            primaryStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	private Scene createMainScene(Stage primaryStage) {
		//Create root holder
        BorderPane root = new BorderPane();
       
        // Create main scene elements - toolbar, exchange rate table, details
        HBox toolBarHBox = createToolBarHBox(primaryStage);               
        exchangeRateTable = createExchangeRateTable();
        detailPane = createDetailPane();
 
        //Set element placements in root
        root.setTop(toolBarHBox);
        root.setCenter(exchangeRateTable);
        root.setRight(detailPane);
       
        // Main scene display settings/staging
        Color scenePaint = new Color(.99, .234, .234, .76);
        Scene mainScene = new Scene(root, 1000, 500);
        mainScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        mainScene.setFill(scenePaint);
        
        return mainScene;
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
        	
        	// Check if the entered user credentials can login or not
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
        
        // Button to change scene to registrations screen
        Button regButton = new Button("New User?");        
        regButton.setOnAction(event -> {
        	System.out.println("Register");
        	primaryStage.setScene(regScene);
        	event.consume();
        });
        
        // Position buttons and fields on the screen
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
        
        // Create User button
        Button createUserButton = new Button("Create User and Login");
        HBox createUserBox = new HBox(createUserButton);
        createUserBox.setSpacing( 10.0d );
        createUserBox.setAlignment(Pos.CENTER );
        createUserBox.setPadding( new Insets(20) );
        regRoot.getChildren().add(createUserBox);
        
        // Handle button to create a new user
        createUserButton.setOnAction(event -> {
        	System.out.println("Reg user!");
        	
        	// Check Zip code - If bad - show alert, else continue
        	int inZipCode = Integer.parseInt(zipField.getText());
        	ZipCodeModel checkZip = zipCodeDAL.getZipCode(inZipCode); 
        	if (checkZip == null) {
        		Alert a = new Alert(AlertType.ERROR);
    			a.setContentText("Invalid zip - please enter a new value");
    			a.show();
    			zipField.setText("");
        		return;
        	}
        	
        	// Create User object
        	UserModel u = new UserModel();
        	u.setName(nameField.getText());
        	u.setEmail(emailField.getText());
        	u.setHashPassword(passwordField.getText());
        	u.setZipCodeId(inZipCode);
        	
    		// Check if the User exists - if not, create
    		UserModel outUser = null;
    		UserModel existingUser = userDAL.getUserByEmail(emailField.getText());
        	if (existingUser == null) {
    			// Create the new user
        		outUser = userDAL.createUser(u);

        		// Set the new user as logged in
    			loggedInUser = outUser;
    			
    			// Call other code here to update the UI to the logged in view
    			System.out.println("- New user created and logged in: " + loggedInUser.getName() );
    			primaryStage.setScene(mainScene);
    		} else {
    			// Display some warning/UI element to tell the user the login failed
    			System.out.println("- Unable to login, check email/pass: " 
    					+ emailField.getText() + " / " + passwordField.getText());

    			Alert a = new Alert(AlertType.ERROR);
    			a.setContentText("A user with this email already exists, please log in");
    			a.show();
    			primaryStage.setScene(loginScene);
    			
    			// Reset the input fields back to blanks if failed
    			nameField.setText("");
    			emailField.setText("");
    			passwordField.setText("");
    			zipField.setText("");
    		}
        	event.consume();
        });
        
        return regScene;
	}
	
	private Scene createFriendsScene(Stage primaryStage) {
		//Create root holder
        BorderPane root = new BorderPane();
       
        Button backBtn = new Button("Back");
        backBtn.setOnAction(event -> {
        	primaryStage.setScene(mainScene);
        });
        
        Label searchLabel = new Label("Search Friends: ");
        TextField searchField = new TextField();
        Button searchBtn = new Button("Add Friend");
        HBox searchBox = new HBox(searchLabel, searchField, searchBtn);
        searchBox.setSpacing( 10.0d );
        searchBox.setPadding( new Insets(2) );
        
        StringProperty searchText = new SimpleStringProperty();
        searchText.bind(searchField.textProperty());
        searchBtn.setOnAction(e -> {
        	String searchInput = searchText.get();
        	System.out.println(searchInput);
        	
        	// Check if name entered matches someone
        	UserModel searchUser = userDAL.getUserByEmail(searchInput);
        	if (searchUser == null) {
        		Alert a = new Alert(AlertType.ERROR);
    			a.setContentText("User not found - try another email");
    			a.show();
    			searchField.setText("");
        		return;
        	} else {
        		// Save new friend to DB
        		FriendModel newFriend = new FriendModel(loggedInUser.getId(), searchUser.getId());
        		newFriend = friendDAL.createFriend(newFriend);
        		
        		if (newFriend == null) {
        			Alert a = new Alert(AlertType.ERROR);
        			a.setContentText("Unable to add friend");
        			a.show();
            		return;
        		}
        		
        		// Save new friend to global list and table
        		FriendTableRow newRow = friendDAL.getFriendRow(loggedInUser.getId(), newFriend.getFriendId());
        		friendList.add(newRow);
        		friendTable.refresh();
        		friendTable.getItems().removeAll(friendTable.getItems());
        		friendTable.getItems().addAll(friendList);
        	}
        	
        });
        VBox centerToolbar = new VBox(searchBox);
        		
        // Make table for friends
        friendTable = new TableView<>();
        friendTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		VBox.setVgrow(friendTable, Priority.ALWAYS );
		
		TableColumn<FriendTableRow, String> colFriendId = new TableColumn<>("Friend ID");
		TableColumn<FriendTableRow, String> colFriendName = new TableColumn<>("Friend Name");
		TableColumn<FriendTableRow, String> colFriendFavNum = new TableColumn<>("# Favorites");
        
		colFriendId.setCellValueFactory( new PropertyValueFactory<>("friendId"));
		colFriendName.setCellValueFactory( new PropertyValueFactory<>("friendName"));
		colFriendFavNum.setCellValueFactory( new PropertyValueFactory<>("numFavs"));
				
		friendTable.getColumns().addAll(colFriendId, colFriendName, colFriendFavNum);
		
		Label favoriteListHeader = new Label("Friend Favorite List: ");
		VBox favoriteVBox = new VBox();
		
		friendTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelect, newSelection) -> {
			favoriteVBox.getChildren().clear();
			if (newSelection!= null) {
				int friendId = newSelection.getFriendId();
				ArrayList<UserFavoriteModel> friendFavorites = userFavoriteDAL.getUserFavoritesByUserId(friendId);
				
				for (int i = 0; i < friendFavorites.size(); i++) {
					Label favString = new Label(friendFavorites.get(i).toString());
					favoriteVBox.getChildren().add(new HBox(favString));
				}
			}
		});
		
		Button deleteFriendBtn = new Button("Delete Friend");
		deleteFriendBtn.setOnAction(e -> {
			// Find the selected row in the Friend table
			FriendTableRow selectedRow = friendTable.getSelectionModel().getSelectedItem();
			if (selectedRow == null) {
				return;
			}
			// Delete that friend from the DB
			boolean check = friendDAL.deleteFriend(loggedInUser.getId(), selectedRow.friendId);
			if (!check) {
				Alert a = new Alert(AlertType.ERROR);
    			a.setContentText("Unable to delete friend");
    			a.show();
        		return;
			}
			// Update the display elements to remove the friend
			friendList.remove(selectedRow);
    		friendTable.refresh();
    		friendTable.getItems().removeAll(friendTable.getItems());
    		friendTable.getItems().addAll(friendList);
		});
		
		VBox suggestionVBox = new VBox();
		
		Label suggestLabel = new Label("Find friends in...");
		Button suggestCountyBtn = new Button("County");
		Button suggestStateBtn = new Button("State");
		Button suggestCityBtn = new Button("City");
		HBox suggestBtnBox = new HBox(suggestStateBtn, suggestCountyBtn, suggestCityBtn);
		suggestCountyBtn.setOnAction(e -> {
			// Clear any previous info
			suggestionVBox.getChildren().clear();
			
			// Check DB for similar friends
			ArrayList<UserModel> friendSuggestions = userDAL.findUsersInX(loggedInUser.getId(), 1);
			
			// Print all friends + info to the suggestion display element
			for (int i = 0; i < friendSuggestions.size(); i++) {
				Label suggestString = new Label(friendSuggestions.get(i).fullInfo());
				suggestionVBox.getChildren().add(new HBox(suggestString));
			}	
		});
		suggestStateBtn.setOnAction(e -> {
			// Clear any previous info
			suggestionVBox.getChildren().clear();
			
			// Check DB for similar friends
			ArrayList<UserModel> friendSuggestions = userDAL.findUsersInX(loggedInUser.getId(), 2);
			
			// Print all friends + info to the suggestion display element
			for (int i = 0; i < friendSuggestions.size(); i++) {
				Label suggestString = new Label(friendSuggestions.get(i).fullInfo());
				suggestionVBox.getChildren().add(new HBox(suggestString));
			}	
		});
		suggestCityBtn.setOnAction(e -> {
			// Clear any previous info
			suggestionVBox.getChildren().clear();
			
			// Check DB for similar friends
			ArrayList<UserModel> friendSuggestions = userDAL.findUsersInX(loggedInUser.getId(), 3);
			
			// Print all friends + info to the suggestion display element
			for (int i = 0; i < friendSuggestions.size(); i++) {
				Label suggestString = new Label(friendSuggestions.get(i).fullInfo());
				suggestionVBox.getChildren().add(new HBox(suggestString));
			}	
		});
        
		HBox leftToolbar = new HBox(backBtn, deleteFriendBtn);
		leftToolbar.setSpacing( 10.0d );
		leftToolbar.setAlignment(Pos.CENTER_LEFT );
		leftToolbar.setPadding( new Insets(2) );
		
        // Create main scene elements
        HBox friendToolbar = new HBox(centerToolbar);               
        VBox friendList = new VBox(friendTable, leftToolbar);
        VBox friendFavorites = new VBox(favoriteListHeader,favoriteVBox);
        VBox friendSuggest = new VBox(suggestLabel, suggestBtnBox, suggestionVBox);
 
        // Bind the VBoxes' widths to the Toolbar width to make them proportional
        friendList.prefWidthProperty().bind(friendToolbar.widthProperty().divide(4));
        friendFavorites.prefWidthProperty().bind(friendToolbar.widthProperty().divide(4));
        friendSuggest.prefWidthProperty().bind(friendToolbar.widthProperty().divide(2));
        
        //Set element placements in root
        root.setTop(friendToolbar);
        root.setLeft(friendList);
        root.setCenter(friendFavorites);
        root.setRight(friendSuggest);

       
        // Main scene display settings/staging
        Color scenePaint = new Color(.99, .234, .234, .76);
        Scene friendScene = new Scene(root, 1000, 500);
        friendScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        friendScene.setFill(scenePaint);
        
        return friendScene;
	}
	
	private Scene createFavoritesScene(Stage primaryStage) {
		//Create root holder
        BorderPane root = new BorderPane();
       
        // Create main scene elements - toolbar, exchange rate table, details               
        VBox favTableSection = createFavTable(primaryStage);
 
        //Set element placements in root
        root.setCenter(favTableSection);
       
        // Main scene display settings/staging
        Color scenePaint = new Color(.99, .234, .234, .76);
        Scene favScene = new Scene(root, 1000, 500);
        favScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        favScene.setFill(scenePaint);
        
        return favScene;
	}
	
	private VBox createFavTable(Stage primaryStage) {
		
		favTable = new TableView<>();
		favTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		VBox.setVgrow(favTable, Priority.ALWAYS );
		
		TableColumn<UserFavoriteModel, String> colFavId = new TableColumn<>("Favorite ID");
		TableColumn<UserFavoriteModel, String> colBase = new TableColumn<>("Base Exchange Rate");
		TableColumn<UserFavoriteModel, String> colTarget = new TableColumn<>("Target Exchange Rate");
		TableColumn<UserFavoriteModel, String> colStartDate = new TableColumn<>("Orig Start Date");
		TableColumn<UserFavoriteModel, String> colAddDate = new TableColumn<>("Added Date");
		
		colFavId.setCellValueFactory( new PropertyValueFactory<>("userFavoriteId"));
		colBase.setCellValueFactory( cellData -> {
			UserFavoriteModel uf = cellData.getValue();
			String symbol = uf.getBaseExchangeRate().getCurrency().getSymbol();
			return new SimpleStringProperty(symbol);
		});
		colTarget.setCellValueFactory( cellData -> {
			UserFavoriteModel uf = cellData.getValue();
			String symbol = uf.getTargetExchangeRate().getCurrency().getSymbol();
			return new SimpleStringProperty(symbol);
		});
		colStartDate.setCellValueFactory( cellData -> {
			UserFavoriteModel uf = cellData.getValue();
			String date = uf.getBaseExchangeRate().getDate().toString();
			return new SimpleStringProperty(date);
		});
		colAddDate.setCellValueFactory( new PropertyValueFactory<>("dateAdded"));
		
		favTable.getColumns().addAll(colFavId, colBase, colTarget, colStartDate, colAddDate);
		
		if (loggedInUser != null) {
			favoriteList = userFavoriteDAL.getUserFavoritesByUserId(loggedInUser.getId());			
		}
		favTable.getItems().addAll(favoriteList);
		
		Button btnDelete = new Button("Delete");
		btnDelete.setOnAction(event -> {
			UserFavoriteModel delete = favTable.getSelectionModel().getSelectedItem();
			if (delete == null) {
				return;
			}
			
			userFavoriteDAL.deleteUserFavorite(delete.getUserFavoriteId());
			favTable.getItems().removeIf(fav -> {
				return fav.getUserFavoriteId() == delete.getUserFavoriteId();
			});
			
			for (ExchangeRateRow rowData: tableRowsData) {
				int rowBase = rowData.getBaseExRateId();
				int rowTar = rowData.getTargetExRateId();
				int delBase = delete.getBaseExchangeRateId();
				int delTar = delete.getTargetExchangeRateId();
				
				if (rowBase != delBase) {
					continue;
				}
				if (rowTar != delTar) {
					continue;
				}
				rowData.isFavorite = false;
			}
			
		});
		
        Button btnAdd = new Button("Add to Table");
        btnAdd.setDisable(true);
        btnAdd.setOnAction(e -> {
        	UserFavoriteModel addFavorite = favTable.getSelectionModel().getSelectedItem();
        	if (addFavorite == null) {
				return;
			}
        	
        	// If we are going to load in the favorite - clear input fields/values 
        	//clearExchangeInputs();
        	//parentAddExchangeRate(bCurrency, tCurrency, inInterval, inStartDate, inEndDate);
        	
        });
        
        Button btnAddDate = new Button("Add with Date");
        btnAddDate.setDisable(true);
        
        Button btnBack= new Button("Back");
        btnBack.setOnAction(event -> {
        	updateExchangeRateTable(exchangeRateTable);
        	primaryStage.setScene(mainScene);
        });
		
		HBox buttonHBox = new HBox(btnDelete, btnAdd, btnAddDate, btnBack);
		buttonHBox.setSpacing(8);
		
		
		VBox tableBox = new VBox(favTable, buttonHBox);
		tableBox.setPadding( new Insets(10) );
		tableBox.setSpacing( 10 );
        
		return tableBox;
	}
	
    private HBox createToolBarHBox(Stage primaryStage) {
     
    	//Create Topbar Section Vboxes
    	VBox toolBarVBoxLeft = createToolBarVBoxLeft();
    	VBox toolBarVBoxCenter = createToolBarVBoxCenter();
    	VBox toolBarVBoxRight = createToolBarVBoxRight(primaryStage);
    	
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
    	fBCurrency = baseCurrChoice;
    	
    	// Get list of base currencies
    	List<CurrencyModel> currencyChoices = currencyDAL.getCurrencyList();
    	
    	// Override the base Currency String Methods - to control text in dropdown
//    	baseCurrChoice.setConverter(new StringConverter<CurrencyModel>() {
//    		@Override
//    		public String toString(CurrencyModel c) {
//    			if (c == null) {
//    				return "";
//    			}
//    			return c.getSymbol();
//    		}
//    		@Override
//            public CurrencyModel fromString(String string) {
//                return null;
//            }
//    	});
    	
    	// Put base currency and label in an HBox to line up
    	baseCurrChoice.getItems().addAll(currencyChoices);
    	HBox bCurrHBox = new HBox(baseCurrencyLabel, baseCurrChoice);
    	bCurrHBox.setSpacing( 10.0d );
    	bCurrHBox.setAlignment(Pos.CENTER_LEFT );
    	bCurrHBox.setPadding( new Insets(2) );
    	baseCurrChoice.setOnAction(event -> bCurrency = baseCurrChoice.getValue());
    	
    	// Target Currency Selection
    	Label targetCurrencyLabel = new Label("Target Currency:");
    	ChoiceBox<CurrencyModel> targetCurrChoice = new ChoiceBox<>();
    	fTCurrency = targetCurrChoice;
    	targetCurrChoice.getItems().addAll(currencyChoices);
    	targetCurrChoice.setOnAction(event -> tCurrency = targetCurrChoice.getValue());
    	
    	// Put target currency and label in an HBox to line up
    	HBox tCurrHBox = new HBox(targetCurrencyLabel, targetCurrChoice);
    	tCurrHBox.setSpacing( 10.0d );
    	tCurrHBox.setAlignment(Pos.CENTER_LEFT );
    	tCurrHBox.setPadding( new Insets(2) );
        
        //Add Left section GUI elements as Vbox children
        toolBarVBoxLeft.getChildren().add(bCurrHBox);
        toolBarVBoxLeft.getChildren().add(tCurrHBox);
        
    	return toolBarVBoxLeft;
    }
    
    private VBox createToolBarVBoxCenter() {

    	VBox toolBarVBoxCenter = new VBox();
    	
    	// Interval Selection field
    	Label intervalLabel = new Label("Interval:");
    	ObservableList <String> intervalOptions = 
    			FXCollections.observableArrayList("day", "week", "month");
    	ChoiceBox<String> intervalChoice = new ChoiceBox<String>(intervalOptions);
    	fInterval = intervalChoice;
    	
    	HBox intervalBox = new HBox(intervalLabel, intervalChoice);
    	intervalBox.setSpacing( 10.0d );
    	intervalBox.setAlignment(Pos.CENTER_LEFT );
    	intervalBox.setPadding( new Insets(2) );
    	toolBarVBoxCenter.getChildren().add(intervalBox);
    	
    	// End Date Selection
    	Label endLabel = new Label("End Date:");
    	DatePicker endField = new DatePicker();
    	fEndDate = endField;
    	LocalDate lastDate = LocalDate.of(2023, 7, 6);
    	endField.setValue(lastDate);
    	endField.setDisable(true);
    	HBox endBox = new HBox(endLabel, endField);
    	endBox.setSpacing( 10.0d );
    	endBox.setAlignment(Pos.CENTER_LEFT );
    	endBox.setPadding( new Insets(2) );
    	
    	// Start Date Selection
    	Label startLabel = new Label("Start Date:");
    	DatePicker startField = new DatePicker();
    	fStartDate = startField;
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
    	startBox.setAlignment(Pos.CENTER_LEFT );
    	startBox.setPadding( new Insets(2) );
    	
    	toolBarVBoxCenter.getChildren().add(startBox);
    	toolBarVBoxCenter.getChildren().add(endBox);
    	
    	// Event Handlers
    	// Interval Events
    	intervalChoice.setOnAction(event -> {
    		inInterval = intervalChoice.getValue();
    		startField.setValue(initialDate);
    		inStartDate = initialDate;
    		endField.setValue(lastDate);
    		inEndDate = lastDate;
    		
    		// Reset the Exchange Rate Table
    		clearAllTableRowData();
    		resetExchangeRateTable(exchangeRateTable);
    		
    		// Get valid end date based on start date:
    		ArrayList<LocalDate> dates = exchangeRateDAL.getNextDatesForSymbolId(
    				inStartDate, bCurrency.getSymbolId(), tCurrency.getSymbolId(),
    				inInterval, TABLE_DATA_ROWS);
    		
    		// Save dates to global state variables (if we get a "dates" result)
    		inDates = dates;
    	});
    	
    	// When the start date is set - find the end date based on interval
    	startField.setOnAction(e -> {
    		// Check to make sure we have the needed inputs (currencies/interval)
    		if (bCurrency == null || tCurrency == null || inStartDate == null
    				|| inInterval.isBlank()) {
    			return;
    		}
    		
    		// If the start date is blanked out - do nothing 
    		if (startField.getValue() == null) {
    			inStartDate = null;
    			return;
    		}
    		
    		// Reset the Exchange Rate Table
    		clearAllTableRowData();
    		resetExchangeRateTable(exchangeRateTable);
    		
    		System.out.println(startField.getValue());
    		inStartDate = startField.getValue();
    		
    		// Get valid end date based on start date:
    		ArrayList<LocalDate> dates = exchangeRateDAL.getNextDatesForSymbolId(
    				inStartDate, bCurrency.getSymbolId(), tCurrency.getSymbolId(),
    				inInterval, TABLE_DATA_ROWS);
    		
    		// Save dates to global state variables (if we get a "dates" result)
    		inDates = dates;
    		
    		if (dates == null) {
    			endField.setValue(null);
    			
    		} else {
    			endField.setValue(dates.get(dates.size()-1));    			
    		}
    		inEndDate = endField.getValue();
    		
    	});
    	
    	return toolBarVBoxCenter;
    }
    
    private VBox createToolBarVBoxRight(Stage primaryStage) {
    	VBox toolBarVBoxRight = new VBox();
    	
    	// Create button elements 
        Button favoriteButton = new Button("View Favorites");
        favoriteButton.setOnAction(event -> {
        	favoriteList = userFavoriteDAL.getUserFavoritesByUserId(loggedInUser.getId());
        	favTable.refresh();
        	favTable.getItems().removeAll(favTable.getItems());
        	favTable.getItems().addAll(favoriteList);
        	primaryStage.setScene(favoritesScene);
        });
        
        Button addButton = new Button("➕ Add Exchange Rate");
        addButton.setOnAction(event -> {
        	// Requires: bCurrency, tCurrency, inInterval, inStartDate, inEndDate
        	parentAddExchangeRate(bCurrency, tCurrency, inInterval, inStartDate, inEndDate);
//        	System.out.println("Add currency exchange!");
//
//        	// Check for all required fields
//        	if (bCurrency == null || tCurrency == null || inInterval == null || inStartDate == null || inEndDate == null) {
//        		Alert a = new Alert(AlertType.ERROR);
//    			a.setContentText("Missing input values - please enter first");
//    			a.show();
//        		return;
//        	}
//        	
//        	// Check if we have too many values in the table
//        	if (tableRowsData.size() >= 8) {
//        		Alert a = new Alert(AlertType.WARNING);
//    			a.setContentText("Too many values in table - please remove one to add another.");
//    			a.show();
//        		return;
//        	}
//        	
//        	// Add the exchange to the list of exchange rates
//        	addNewExchangeRateTable();
//        	// Re-generate the table to update with the new rate
//        	updateExchangeRateTable(exchangeRateTable);
        });
        
        Button friendButton = new Button("Friends");
        friendButton.setOnAction(event -> {
        	// Get list of friends, save to global
        	friendList = friendDAL.getFriendList(loggedInUser.getId()); 
        	friendTable.getItems().removeAll(friendTable.getItems());
        	friendTable.getItems().addAll(friendList);
        	// Update the friend list based on logged in user
        	primaryStage.setScene(friendsScene);
        });

    	//Set elements as children of VBox
    	
        toolBarVBoxRight.getChildren().add(favoriteButton);
        toolBarVBoxRight.getChildren().add(addButton);
        toolBarVBoxRight.getChildren().add(friendButton);

        toolBarVBoxRight.setSpacing( 2.0d );
        toolBarVBoxRight.setAlignment(Pos.CENTER_LEFT );
        toolBarVBoxRight.setPadding( new Insets(2) );
        
    	return toolBarVBoxRight;
    }
    
    private void parentAddExchangeRate(CurrencyModel bCurrency, CurrencyModel tCurrency, 
    		String inInterval, LocalDate inStartDate, LocalDate inEndDate) {
    	System.out.println("Add currency exchange!");

    	// Check for all required fields
    	if (bCurrency == null || tCurrency == null || inInterval == null || inStartDate == null || inEndDate == null) {
    		Alert a = new Alert(AlertType.ERROR);
			a.setContentText("Missing input values - please enter first");
			a.show();
    		return;
    	}
    	
    	// Check if we have too many values in the table
    	if (tableRowsData.size() >= 8) {
    		Alert a = new Alert(AlertType.WARNING);
			a.setContentText("Too many values in table - please remove one to add another.");
			a.show();
    		return;
    	}
    	
    	// Add the exchange to the list of exchange rates
    	addNewExchangeRateTable(bCurrency, tCurrency, inInterval, inStartDate, inEndDate);
    	// Re-generate the table to update with the new rate
    	updateExchangeRateTable(exchangeRateTable);
    }
    
    private void clearExchangeInputs() {
    	tCurrency = null;
    	bCurrency= null;
    	inInterval = null;
    	inStartDate = null;
    	inEndDate = null;
    	inDates = null;
    	
    	fBCurrency.setValue(null);
    	fTCurrency.setValue(null);
    	fInterval.setValue(null);
    	fStartDate.setValue(null);
    	fEndDate.setValue(null);
    }
    
    private void addNewExchangeRateTable(CurrencyModel bCurrency, CurrencyModel tCurrency, 
    		String inInterval, LocalDate inStartDate, LocalDate inEndDate) {
    	// Insert new Exchange Rate
    	ArrayList<ExchangeRateModel> baseExRates = exchangeRateDAL
    			.getExchangeRatesOverDateRange(bCurrency.getSymbolId(), inInterval, inDates);
		System.out.println("- Found Base ExRates: " + baseExRates);
		
		ArrayList<ExchangeRateModel> targetExRates = exchangeRateDAL
				.getExchangeRatesOverDateRange(tCurrency.getSymbolId(), inInterval, inDates);
		System.out.println("- Found TargetExRates: " + targetExRates);
		
		// Save the new row to our table data
		ExchangeRateRow newRow = new ExchangeRateRow(baseExRates, targetExRates);
		tableRowsData.add(newRow);
		
		// Update the table to re-draw all rows
		updateExchangeRateTable(exchangeRateTable);
    }
    
    private void resetExchangeRateTable(GridPane table) {
    	// Clear Table
    	table.getChildren().clear();
    	// Add Header with dates
    	addTableDatesHeader(table);
    }
    
    private void updateExchangeRateTable(GridPane table) {
//    	// Clear Table
    	resetExchangeRateTable(table);
    	
    	// Loop through the table data and add a row to the grid for each
    	int row = 1;
    	for (int i = 0; i < tableRowsData.size(); i++) {
    		ExchangeRateRow currentRow = tableRowsData.get(i);
    		ArrayList<ExchangeRateModel> calcValues = currentRow.calcValues;
    		boolean isFav = currentRow.isFavorite;
    		
    		Label buttonLabel = new Label(currentRow.exchangeRateLabel());
    		Button favButton = new Button(isFav ? "Unfav": "Fav");
    		HBox hButtonBox = new HBox (5, favButton, buttonLabel);
    		VBox vButtonBox = new VBox(hButtonBox);
    		vButtonBox.setPadding( new Insets(10) );
    		table.add(vButtonBox, 0, row);
    		
    		final int inRow = row;
    		favButton.setOnAction(event -> {
    			toggleTableFavorite(inRow - 1);
    		});
    		
    		int col = 1;
    		for (int j = 0; j < calcValues.size(); j++) {
    			// Just do open price for now...
    			double open = calcValues.get(j).getOpen();
    			Label dataLabel = new Label(String.format("%.2f", open));
    			VBox cellBox = new VBox(dataLabel);
    			cellBox.setPadding( new Insets(10) );
    			
    			cellBox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    				selectTableRow(inRow);
    			});
    			
    			table.add(cellBox, col, row);
    			col++;
    		}
    		
    		// Add right side delete button
    		Label deleteLabel = new Label("❌");
    		VBox deleteBox = new VBox(deleteLabel);
    		deleteBox.setPadding( new Insets(10) );
    		deleteBox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    			System.out.println("Delete row " + inRow);
    			deleteTableRow(inRow - 1);
    		});
    		table.add(deleteBox, TABLE_DELETE_COL, row);
    		
    		row++;
        }
    }
    
    private void clearAllTableRowData() {
    	tableRowsData.removeAll(tableRowsData);
    }
    
    private void deleteTableRow(int row) {
    	// Remove the deleted row from the list of exchange rates
    	tableRowsData.remove(row);
    	// Re-generate the table without the removed row
    	updateExchangeRateTable(exchangeRateTable);
    }
    
    private void selectTableRow(int row) {
    	ObservableList<Node> tableChildren = exchangeRateTable.getChildren();
    	
    	// Update Row highlighting
    	for (Node node: tableChildren) {
    		int nodeRow = GridPane.getRowIndex(node);
    		int nodeCol = GridPane.getColumnIndex(node);
    		if(nodeRow == row && nodeCol != TABLE_DELETE_COL) {
    			if (nodeRow == selectedRow) {
    				node.setStyle("-fx-background-color: null");    				
    			} else {
    				node.setStyle("-fx-background-color: #00FFFF");
    			}
    		} else {
    			node.setStyle("-fx-background-color: null");
    		}
    	} 	
    	
    	// Handle tracking selected row
    	if (selectedRow == row) {
    		selectedRow = -1;
    		clearDetailData();
    	} else {
    		selectedRow = row;
    		updateDetailPane();
    	}
    }
    
    private void toggleTableFavorite(int row) {
    	// Identify the row and get the base/exchange info
    	ExchangeRateRow eRow = tableRowsData.get(row);

    	int favUserId = loggedInUser.getId();
    	int favBaseRate = eRow.getBaseExRateId();
    	int favTarRate = eRow.getTargetExRateId();
    	LocalDate favDate = LocalDate.now();
    	
    	if (eRow.isFavorite) {
//    		boolean delCheck = userFavoriteDAL.deleteUserFavoriteSearch(favUserId, favBaseRate, favTarRate);
    		boolean delCheck = userFavoriteDAL.deleteUserFavorite(eRow.userFavoriteId);
    		eRow.isFavorite = false;
    		
    	} else {
    		// Save to the favorite DB
    		UserFavoriteModel uf = new UserFavoriteModel();
        	uf.setUserId(favUserId);
        	uf.setBaseExchangeRateId(favBaseRate);
        	uf.setTargetExchangeRateId(favTarRate);
        	uf.setDateAdded(favDate);
        	UserFavoriteModel newUf = userFavoriteDAL.createUserFavorite(uf);
        	eRow.userFavoriteId = newUf.getUserFavoriteId();
        	
        	eRow.isFavorite = true;
    	}
    	
    	// Reload the table
    	updateExchangeRateTable(exchangeRateTable);
    }
    
    private void addTableDatesHeader(GridPane table) {
    	if (inDates == null || inDates.size()< TABLE_DATA_ROWS) {
    		addTableDatesEmptyHeader(table);
    		return;
    	}
    	
    	// Add date header to the table based on the input dates    	
    	int dateCounter = 0;
    	ArrayList<LocalDate> dates = inDates;
    	for (int col = 0; col < TABLE_DATA_ROWS + 2; col++) {
    		boolean notFirstLast = (col > 0 && col < TABLE_DELETE_COL);
    		Label label;
    		if (notFirstLast) {
    			// The 7 middle columns are dates
    			label = new Label(dates.get(dateCounter).toString());
    			dateCounter++;
    		} else {
    			// The first and the last columns should not be dates
    			label = new Label("   ");
    		}
        	VBox dateBox = new VBox(label);
    		dateBox.setPadding( new Insets(10) );
        	table.add(dateBox, col, 0);
        }
    }

    private void addTableDatesEmptyHeader(GridPane table) {
    	// Create Empty exchange rate table
    	for (int col = 0; col < TABLE_DATA_ROWS + 2; col++) {
    		// Add placeholder values for the 7 middle columns in the table
    		boolean notFirstLast = (col > 0 && col < TABLE_DELETE_COL);
    		Label label = new Label(notFirstLast ? "Date " + (col): "  ");
    		VBox dateBox = new VBox(label);
    		dateBox.setPadding( new Insets(10) );
        	table.add(dateBox, col, 0);
        }
    }
      
    private VBox createDetailPane() {
    	VBox rightPane = new VBox();
    	
    	// Testing binding
//    	Label testLabel = new Label();
//		TextField testField= new TextField();
//	    HBox testBox = new HBox(testLabel, testField);
//	    testLabel.textProperty().bind(testField.textProperty());
//	    rightPane.getChildren().add(testBox);
	    
    	// Exchange Rate info Section
    	Label exRateTitle = new Label("Full Exchange Rate Info");
    	rightPane.getChildren().add(exRateTitle);
    	
    	Label openClose = new Label(" - Open/Close: ");
	   	Label openCloseVal = new Label();
	   	openCloseVal.textProperty().bind(Bindings.format("%.2f / %.2f", dOpen, dClose));
	   	HBox openCloseBox = new HBox(openClose, openCloseVal);
	   	rightPane.getChildren().add(openCloseBox);
	   	
	   	Label highLow = new Label(" - High/Low: ");
	   	Label highLowVal = new Label();
	   	highLowVal.textProperty().bind(Bindings.format("%.2f / %.2f", dHigh, dLow));
	   	HBox highLowBox = new HBox(highLow, highLowVal);
	   	rightPane.getChildren().add(highLowBox);
	   	
	   	Label volume = new Label(" - Volume: ");
	   	Label volumeVal = new Label();
	   	volumeVal.textProperty().bind(Bindings.format("%.2f", dVolume));
	   	HBox volumeBox = new HBox(volume, volumeVal);
	   	rightPane.getChildren().add(volumeBox);

	   	// Demographics section
	   	Label currDemoTitle = new Label("Currency Demographics");
    	rightPane.getChildren().add(currDemoTitle);
    	
    	// Country
    	Label countryLabel = new Label(" - Country: ");
	   	Label country1 = new Label();
	   	country1.textProperty().bind(dCountry1);
	   	Label country2 = new Label();
	   	country2.textProperty().bind(dCountry2);
	   	HBox countryBox = new HBox(10, countryLabel, country1, country2);
	   	rightPane.getChildren().add(countryBox);
	   	
	   	// GDP
	   	Label gdpLabel = new Label(" - GDP: ");
	   	Label gdp1 = new Label();
	   	gdp1.textProperty().bind(Bindings.format("%,.2f", dGdp1));
	   	Label gdp2 = new Label();
	   	gdp2.textProperty().bind(Bindings.format("%,.2f", dGdp2));
	   	HBox gdpBox = new HBox(10, gdpLabel, gdp1, gdp2);
	   	rightPane.getChildren().add(gdpBox);
	   	
	   	// Debt
	   	Label debtLabel = new Label(" - Debt: ");
	   	Label debt1 = new Label();
	   	debt1.textProperty().bind(Bindings.format("%,.2f", dDebt1));
	   	Label debt2 = new Label();
	   	debt2.textProperty().bind(Bindings.format("%,.2f", dDebt2));
	   	HBox debtBox = new HBox(10, debtLabel, debt1, debt2);
	   	rightPane.getChildren().add(debtBox);
	   	
	   	// Land Area
	   	Label landAreaLabel = new Label(" - Land Area: ");
	   	Label landArea1 = new Label();
	   	landArea1.textProperty().bind(Bindings.format("%,d", dLandArea1));
	   	Label landArea2 = new Label();
	   	landArea2.textProperty().bind(Bindings.format("%,d", dLandArea2));
	   	HBox landAreaBox = new HBox(10, landAreaLabel, landArea1, landArea2);
	   	rightPane.getChildren().add(landAreaBox);
	   	
	   	// Population Density
	   	Label densityLabel = new Label(" - Density: ");
	   	Label density1 = new Label();
	   	density1.textProperty().bind(Bindings.format("%,.2f", dDensity1));
	   	Label density2 = new Label();
	   	density2.textProperty().bind(Bindings.format("%,.2f", dDensity2));
	   	HBox densityBox = new HBox(10, densityLabel, density1, density2);
	   	rightPane.getChildren().add(densityBox);
	   	
	   	rightPane.setPadding( new Insets(10) );
	   	
    	return rightPane;
    }
    
   private void updateDetailPane() {
	   // Get Exchange Rates to display for a given row
	   ExchangeRateRow selectedExRateRow = tableRowsData.get(selectedRow - 1);
	   ExchangeRateModel averages = selectedExRateRow.calculateAvgValues();
   	
	   // Get Currencies
	   CurrencyModel baseCurrency = selectedExRateRow.getBaseCurrency();
	   CurrencyModel tarCurrency = selectedExRateRow.getTarCurrency();
  
	   // Update the combined Exchange Rate info
	   dOpen.set(averages.getOpen());
	   dClose.set(averages.getClose()); 
	   dHigh.set(averages.getHigh()); 
	   dLow.set(averages.getLow()); 
	   dVolume.set(averages.getVolume()); 
	   
	   // Update the currency demo info for each 
	   dCountry1.set(baseCurrency.getCountry());
	   dCountry2.set(tarCurrency.getCountry());
	   dGdp1.set(baseCurrency.getGdp());
	   dGdp2.set(tarCurrency.getGdp());
	   dDebt1.set(baseCurrency.getDebt());
	   dDebt2.set(tarCurrency.getDebt());
	   dDensity1.set(baseCurrency.getDensity());
	   dDensity2.set(tarCurrency.getDensity());
	   dLandArea1.set(baseCurrency.getLandArea());
	   dLandArea2.set(tarCurrency.getLandArea());
   }
    
    private void clearDetailData() {
    	dOpen.set(0);
 	   	dClose.set(0); 
 	   	dHigh.set(0);
 	   	dLow.set(0); 
 	   	dVolume.set(0); 
    	
    	dCountry1.set("");
 	   	dCountry2.set("");
 	   	dGdp1.set(0);
 	   	dGdp2.set(0);
 	   	dDebt1.set(0);
 	   	dDebt2.set(0);
 	   	dDensity1.set(0);
 	   	dDensity2.set(0);
 	   	dLandArea1.set(0);
 	   	dLandArea2.set(0);
    }
    
    private GridPane createExchangeRateTable() {
        GridPane gridPane = new GridPane();
        // Add Empty Date Headers
        addTableDatesEmptyHeader(gridPane);
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
    	friendDAL = new FriendDAL(dc);
    	
    	launch(args);
    }	    
}

	

