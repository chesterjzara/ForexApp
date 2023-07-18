import java.util.ArrayList;

public class ForexApp {

	public static void main(String[] args) {
		//Connection conn = null;
		// String url = "jdbc:sqlite:test_db";
		// String sql = "SELECT * FROM users";
		
		//conn = DriverManager.getConnection(url);
		DataConnection dc = new DataConnection();
		UserDAL userDAL = new UserDAL(dc);
		CurrencyDAL currencyDAL = new CurrencyDAL(dc);
		FavoriteDAL favoriteDAL = new FavoriteDAL(dc);
		
		// Test Retrieving data
		//UserModel testUser = userDAL.retrieveUser(123);
		//System.out.println(testUser);
		
		// Test Creating data
		int newUserId = 5;
		if (userDAL.getUser(newUserId) == null) {
			UserModel newUser = new UserModel(newUserId, "TestGuy", "Testguy@email.com", "Smith");
			boolean ret = userDAL.createUser(newUser);
			System.out.println("Created new user: " + ret);
		} else {
			System.out.println("No new user");
		}
		
		CurrencyModel currency = currencyDAL.retrieveCurrency(1);
		System.out.println(currency);
		
		// Testing Favorite Lookup and Creation
		int testFavId = 1;
		FavoriteModel testFav = favoriteDAL.getFavoriteById(testFavId);
		System.out.println(testFav);
		
		int newFavId = 4;
		if (favoriteDAL.getFavoriteById(newFavId) == null) {
			FavoriteModel newFav = new FavoriteModel(newFavId, 4, 13, 15);
			boolean ret = favoriteDAL.createFavorite(newFav);
			System.out.println("Created new fav: " + ret);
		} else {
			System.out.println("No new fav created");
		}
		
		ArrayList<FavoriteModel> multiResults = new ArrayList<FavoriteModel>();
		multiResults = favoriteDAL.getFavoritesByUser(3);
		for (FavoriteModel e : multiResults) {
			System.out.println(e);
		}
		
		
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