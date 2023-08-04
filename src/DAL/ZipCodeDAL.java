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
	
	public ZipCodeModel getZipCode(int zipCode) {
		String sql = "SELECT * FROM zip_code z WHERE z.zip_code=" + zipCode;
		try {
			ResultSet rs = connection.getConn().createStatement().executeQuery(sql);
			rs.next();
			
			if (rs.getInt("zip_code") > 0) {
				ZipCodeModel z = parseZipCodeResult(rs);
				return z;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public ZipCodeModel createZipCode (ZipCodeModel zipCode) {
		// Check if zip code already exists and return it
		ZipCodeModel checkZip = getZipCode(zipCode.getZipCode());
		if (checkZip != null) {
			return checkZip;
		}
		
		String insertSql = "INSERT INTO zip_code VALUES (?, ?, ?);";
		int ret = 0;
		try {
			PreparedStatement pstmt = this.connection.getConn()
					.prepareStatement(insertSql);
			pstmt.setInt(1, zipCode.getZipCode());
			pstmt.setString(2, zipCode.getCity());
			pstmt.setString(3, zipCode.getState());
			
			ret = pstmt.executeUpdate();
			if (ret>0) {
				return zipCode;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private ZipCodeModel parseZipCodeResult(ResultSet rs) {
		ZipCodeModel z = new ZipCodeModel();
		try {
			z.setZipCode(rs.getInt("zip_code"));
			z.setCity(rs.getString("city"));
			z.setState(rs.getString("state"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return z;
	}
}
