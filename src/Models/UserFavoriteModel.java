package Models;

public class UserFavoriteModel {
	private int id;
	private int favoriteId;
	private FavoriteModel favorite;
	private int userId;
	private UserModel user;
	
	public UserFavoriteModel() {}

	public UserFavoriteModel(int id, int favoriteId, FavoriteModel favorite, int userId, UserModel user) {
		super();
		this.id = id;
		this.favoriteId = favoriteId;
		this.favorite = favorite;
		this.userId = userId;
		this.user = user;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFavoriteId() {
		return favoriteId;
	}

	public void setFavoriteId(int favoriteId) {
		this.favoriteId = favoriteId;
	}

	public FavoriteModel getFavorite() {
		return favorite;
	}

	public void setFavorite(FavoriteModel favorite) {
		this.favorite = favorite;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}	
}
