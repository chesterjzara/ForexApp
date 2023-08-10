package Models;

public class FriendModel {
	private int userId;
	private UserModel user;
	private int friendId;
	private UserModel friend;
	
	public FriendModel() {}
	
	public FriendModel(int userId, int friendId) {
		this.userId = userId;
		this.friendId = friendId;
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

	public int getFriendId() {
		return friendId;
	}

	public void setFriendId(int friendId) {
		this.friendId = friendId;
	}

	public UserModel getFriend() {
		return friend;
	}

	public void setFriend(UserModel friend) {
		this.friend = friend;
	}
	
}
