package DAL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Models.*;

public class ZipCodeDAL {
	private DataConnection connection;
	
	public static String zipCodeTableDDL = 
			"CREATE TABLE zip_code ("
					+ "zip_code INTEGER PRIMARY KEY NOT NULL UNIQUE,"
					+ "city     TEXT,"
					+ "state    TEXT);";
	
	public ZipCodeDAL(DataConnection connection) {
        this.connection = connection;
    }
}
