package Models;

import java.time.LocalDate;

public class UserFavoriteModel {
	private int userFavoriteId;
	private int userId;
	private UserModel user;
	private int baseExchangeRateId;
	private ExchangeRateModel baseExchangeRate;
	private int targetExchangeRateId;
	private ExchangeRateModel targetExchangeRate;
	private LocalDate dateAdded;
	
	public UserFavoriteModel() {}

	public UserFavoriteModel(int userFavoriteId, int userId, int baseExchangeRateId, 
			int targetExchangeRateId, LocalDate dateAdded) {
		this.userFavoriteId = userFavoriteId;
		this.userId = userId;
		this.baseExchangeRateId = baseExchangeRateId;
		this.targetExchangeRateId = targetExchangeRateId;
		this.dateAdded = dateAdded;
	}
	
	public int getUserFavoriteId() {
		return userFavoriteId;
	}

	public void setUserFavoriteId(int userFavoriteId) {
		this.userFavoriteId = userFavoriteId;
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

	public int getBaseExchangeRateId() {
		return baseExchangeRateId;
	}

	public void setBaseExchangeRateId(int baseExchangeRateId) {
		this.baseExchangeRateId = baseExchangeRateId;
	}

	public ExchangeRateModel getBaseExchangeRate() {
		return baseExchangeRate;
	}

	public void setBaseExchangeRate(ExchangeRateModel baseExchangeRate) {
		this.baseExchangeRate = baseExchangeRate;
	}

	public int getTargetExchangeRateId() {
		return targetExchangeRateId;
	}

	public void setTargetExchangeRateId(int targetExchangeRateId) {
		this.targetExchangeRateId = targetExchangeRateId;
	}

	public ExchangeRateModel getTargetExchangeRate() {
		return targetExchangeRate;
	}

	public void setTargetExchangeRate(ExchangeRateModel targetExchangeRate) {
		this.targetExchangeRate = targetExchangeRate;
	}

	public LocalDate getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(LocalDate dateAdded) {
		this.dateAdded = dateAdded;
	}

	public String toString() {
		return "["+userFavoriteId+"] From " + baseExchangeRate.getCurrency().getSymbol() + " to " + targetExchangeRate.getCurrency().getSymbol() 
				+ " on " + dateAdded.toString();
	}
	
}
