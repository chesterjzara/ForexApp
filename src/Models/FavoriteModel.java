// TODO - delete - probably don't need - marked deprecated

package Models;

@Deprecated 
public class FavoriteModel {
	private int id;
	private int baseExchId;
	private int tarExchId;
	
	public FavoriteModel() {}
	
	public FavoriteModel(int id, int baseExchId, int tarExchId) {
		this.setId(id);
		this.setBaseExchId(baseExchId);
		this.setTarExchId(tarExchId);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
		return "From " + baseExchId + " to " + tarExchId + "[" + id + "]"; 
	}
	
}
