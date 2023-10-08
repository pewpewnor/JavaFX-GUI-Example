package TableItem;

public class DetailItem {
	private Integer transactionID, watchID;
	private String watchName, watchBrand;
	private Integer watchPrice, quantity, subTotal;
	
	public DetailItem(Integer transactionID, Integer watchID, String watchName, String watchBrand, Integer watchPrice,
			Integer quantity) {
		this.transactionID = transactionID;
		this.watchID = watchID;
		this.watchName = watchName;
		this.watchBrand = watchBrand;
		this.watchPrice = watchPrice;
		this.quantity = quantity;
		this.subTotal = watchPrice * quantity;
	}
	
	public Integer getTransactionID() {
		return transactionID;
	}
	
	public String getTransactionIDFormatted() {
		return "" + transactionID;
	}
	
	public Integer getWatchID() {
		return watchID;
	}
	
	public String getWatchIDFormatted() {
		return "" + watchID;
	}
	
	public String getWatchName() {
		return watchName;
	}
	
	public String getWatchBrand() {
		return watchBrand;
	}
	
	public Integer getWatchPrice() {
		return watchPrice;
	}
	
	public String getWatchPriceFormatted() {
		return "$" + watchPrice;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	
	public String getSubTotal() {
		return "$" + subTotal;
	}
	
}
