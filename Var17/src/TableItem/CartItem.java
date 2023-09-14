package TableItem;

public class CartItem {
	private Integer userID, watchID, quantity;

	public CartItem(Integer userID, Integer watchID, Integer quantity) {
		this.userID = userID;
		this.watchID = watchID;
		this.quantity = quantity;
	}

	public Integer getUserID() {
		return userID;
	}
	
	public String getUserIDFormatted() {
		return "" + userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public Integer getWatchID() {
		return watchID;
	}
	
	public String getWatchIDFormatted() {
		return "" + watchID;
	}

	public void setWatchID(Integer watchID) {
		this.watchID = watchID;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
}
