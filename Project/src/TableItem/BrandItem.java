package TableItem;

public class BrandItem {
	private Integer brandID;
	private String brandName;
	
	public BrandItem(Integer brandID, String brandName) {
		this.brandID = brandID;
		this.brandName = brandName;
	}
	
	public Integer getBrandID() {
		return brandID;
	}
	
	public String getBrandIDFormatted() {
		return "" + brandID;
	}
	
	public void setBrandID(Integer brandID) {
		this.brandID = brandID;
	}
	
	public String getBrandName() {
		return brandName;
	}
	
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
}
