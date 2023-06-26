import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ForexApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection conn = null;
		String url = "jdbc:sqlite:C:\\data\\test_db";
		String sql = "SELECT * FROM users";
		
		//conn = DriverManager.getConnection(url);
		DataConnection dc = new DataConnection();
		UserDAL userDAL = new UserDAL(dc);
		
		UserModel testUser = userDAL.retrieveData(123);
		
		System.out.println(testUser);
		
//		Statement stmt = dc.getConn().createStatement();
//        ResultSet rs = stmt.executeQuery(sql);
//        
//        while(rs.next()){
//            //Display values
//            System.out.print("ID: " + rs.getInt("id"));
//            System.out.print(", Age: " + rs.getInt("age"));
//            System.out.print(", First: " + rs.getString("first"));
//            System.out.println(", Last: " + rs.getString("last"));
//        }
	}
}