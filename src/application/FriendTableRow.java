package application;

import java.util.ArrayList;

import DAL.*;
import Models.*;

public class FriendTableRow {

	public int friendId;
	public String friendName;
	public String friendEmail;
	public String friendCity;
//	public ArrayList<UserFavoriteModel> friendFavorites;
	public int numFavs;
	
	public FriendTableRow(FriendModel friend, int numFavs) {
		this.friendId = friend.getUserId();
		this.friendName = friend.getFriend().getName();
		this.friendCity = friend.getFriend().getZipCode().getCity();
//		this.friendFavorites = favorites;
		this.numFavs= numFavs;
	}
	
	public FriendTableRow() {
	}

	/**
	 * @return the friendId
	 */
	public int getFriendId() {
		return friendId;
	}

	/**
	 * @param friendId the friendId to set
	 */
	public void setFriendId(int friendId) {
		this.friendId = friendId;
	}

	/**
	 * @return the friendName
	 */
	public String getFriendName() {
		return friendName;
	}

	/**
	 * @param friendName the friendName to set
	 */
	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}

	/**
	 * @return the friendEmail
	 */
	public String getFriendEmail() {
		return friendEmail;
	}

	/**
	 * @param friendEmail the friendEmail to set
	 */
	public void setFriendEmail(String friendEmail) {
		this.friendEmail = friendEmail;
	}

	/**
	 * @return the friendCity
	 */
	public String getFriendCity() {
		return friendCity;
	}

	/**
	 * @param friendCity the friendCity to set
	 */
	public void setFriendCity(String friendCity) {
		this.friendCity = friendCity;
	}

	/**
	 * @return the numFavs
	 */
	public int getNumFavs() {
		return numFavs;
	}

	/**
	 * @param numFavs the numFavs to set
	 */
	public void setNumFavs(int numFavs) {
		this.numFavs = numFavs;
	}
	
}
