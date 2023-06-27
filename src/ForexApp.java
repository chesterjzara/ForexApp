
public class ForexApp {

	public static void main(String[] args) {
		//Connection conn = null;
		// String url = "jdbc:sqlite:test_db";
		// String sql = "SELECT * FROM users";
		
		//conn = DriverManager.getConnection(url);
		DataConnection dc = new DataConnection();
		UserDAL userDAL = new UserDAL(dc);
		
		// Test Retrieving data
		//UserModel testUser = userDAL.retrieveUser(123);
		//System.out.println(testUser);
		
		// Test Creating data
		UserModel newUser = new UserModel(1, 20, "Testguy", "Smith");
		boolean ret = userDAL.createUser(newUser);
		
		System.out.println("Success?: " + ret);
		
		
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