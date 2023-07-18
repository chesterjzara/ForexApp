
public class FavoriteModel {
	private int id;
	private int userId;
	private int baseExchId;
	private int tarExchId;
	
	public FavoriteModel() {}
	
	public FavoriteModel(int id, int userId, int baseExchId, int tarExchId) {
		this.setId(id);
		this.setUserId(userId);
		this.setBaseExchId(baseExchId);
		this.setTarExchId(tarExchId);
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

	public int getTarExchId() {
		return tarExchId;
	}

	public void setTarExchId(int tarExchId) {
		this.tarExchId = tarExchId;
	}
	
	public String toString() {
		return "From " + baseExchId + " to " + tarExchId + "[" + id 
				+ "] - " + userId; 
	}
	
}
