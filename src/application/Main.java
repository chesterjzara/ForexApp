package application;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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


public class Main extends Application {


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
	            Scene scene = new Scene(root, 800, 500);
	            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	            primaryStage.setScene(scene);
	            scene.setFill(scenePaint);
	            primaryStage.show();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
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
	    	
	    	
	    	//Currency Labels
	    	Label baseCurrencyLabel = new Label("Base Currency:");
	    	Label targetCurrencyLabel = new Label("Target Currency:");
	    	
	        
	        //Add Left section GUI elements as Vbox children
	        
	        toolBarVBoxLeft.getChildren().add(baseCurrencyLabel);
	        toolBarVBoxLeft.getChildren().add(targetCurrencyLabel);
	        
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
	        launch(args);
	    }
	    
	    
	}

	

