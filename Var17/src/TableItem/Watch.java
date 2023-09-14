package TableItem;

public class Watch {
	private Integer watchID;
	private String brand, name;
	private Integer price, stock;
	
	public Watch(Integer watchID, String brand, String name, Integer price, Integer stock) {
		this.watchID = watchID;
		this.brand = brand;
		this.name = name;
		this.price = price;
		this.stock = stock;
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

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPrice() {
		return price;
	}
	
	public String getPriceFormatted() {
		return "$" + price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}
	
}
