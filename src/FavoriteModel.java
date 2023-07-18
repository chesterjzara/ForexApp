
public class FavoriteModel {
	private int id;
	private int userId;
	private int baseExchId;
	private int targetExchId;
	
	public FavoriteModel() {}
	
	public FavoriteModel(int id, int userId, int baseExchId, int targetExchId) {
		this.setId(id);
		this.setUserId(userId);
		this.setBaseExchId(baseExchId);
		this.setTargetExchId(targetExchId);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getBaseExchId() {
		return baseExchId;
	}

	public void setBaseExchId(int baseExchId) {
		this.baseExchId = baseExchId;
	}

	public int getTargetExchId() {
		return targetExchId;
	}

	public void setTargetExchId(int targetExchId) {
		this.targetExchId = targetExchId;
	}
	
	public String toString() {
		return "From " + baseExchId + " to " + targetExchId + "[" + id 
				+ "] - " + userId; 
	}
	
}
