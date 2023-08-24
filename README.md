# ForexApp

## Setup steps
* In Eclipse in the menu File > Import > Maven > Existing Maven Projects
* Once the project is loaded, update the Run Configurations of the Main.java file in the "application" module
    * Under the Arguments tab, add the VM arguments:
        * --module-path C:\data\javafx-sdk-20.0.2\lib --add-modules=ALL-MODULE-PATH
        * This line updates the module path to point to a local copy of the javafx sdk
        * This can be downloaded here https://openjfx.io/openjfx-docs/#install-javafx
	* Now the application should be able to be ran with this run config and display a gui app
* We also must configure an environment variable on your local machine to specify the SQLite database location
    * Create a new ".env" file in the main ForexApp directory
        * Set the DATABASE_URL variable to a location on your machine where the SQLite database will be created
        * Example: DATABASE_URL=C:\\data\\test_db2.db
* Lastly, before running the application we must run the "ForexAppSetup.java" file in the Forex.App package
     * This will create the database and create the tables and sample data used by our app
     * You should see console output indicating that the DB is created and that various tables are populated with the included CSV data