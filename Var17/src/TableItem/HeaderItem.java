package TableItem;

public class HeaderItem {
	private Integer transactionID, userID;
	private String transactionDate;
	
	public HeaderItem(Integer transactionID, Integer userID, String transactionDate) {
		this.transactionID = transactionID;
		this.userID = userID;
		this.transactionDate = transactionDate;
	}

	public Integer getTransactionID() {
		return transactionID;
	}
	
	public String getTransactionIDFormatted() {
		return "" + transactionID;
	}

	public void setTransactionID(Integer transactionID) {
		this.transactionID = transactionID;
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

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	
}
