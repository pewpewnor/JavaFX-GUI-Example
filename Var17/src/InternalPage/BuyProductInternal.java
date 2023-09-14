package InternalPage;

import java.sql.Date;
import java.util.ArrayList;
import TableItem.CartItem;
import TableItem.Watch;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class BuyProductInternal extends InternalPage {
	HBox midContainer, bottomContainer;
	
	TableView<Watch> productTable;
	TableView<CartItem> cartTable;
	
	Label picked, quantityLbl;
	
	Spinner<Integer> quantity;
	Button addBtn, checkoutBtn, clearBtn;
	
	Watch selectedWatch = null;
	
	@Override
	public void init() {
		midContainer = new HBox();
		bottomContainer = new HBox();
		
		productTable = new TableView<>();
		cartTable = new TableView<>();
		
		picked = new Label("Selected Watch: None");
		quantityLbl = new Label("Quantity: ");
		
		quantity = new Spinner<>(0, 99, 0);
		
		addBtn = new Button("Add Watch To Cart");
		checkoutBtn = new Button("Checkout");
		clearBtn = new Button("Clear Cart");
	}

	@Override
	public void set() {
		this.setPadding(new Insets(10));
		this.setSpacing(10);
		
		midContainer.setSpacing(10);
		bottomContainer.setSpacing(30);
		
		midContainer.setAlignment(Pos.CENTER);
		bottomContainer.setAlignment(Pos.CENTER);
		
		quantity.setPromptText("Quantity");
		
		Font lblFont = Font.font("", FontWeight.BOLD, 20);
		quantityLbl.setFont(lblFont);
	}
	
	private void setProductTable() {
		TableColumn<Watch, String> watchIDCol = new TableColumn<>("Watch ID");
		watchIDCol.setCellValueFactory(new PropertyValueFactory<>("watchIDFormatted"));
		watchIDCol.setMinWidth(100);
		
		TableColumn<Watch, String> nameCol = new TableColumn<>("Watch Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		nameCol.setMinWidth(270);
		
		TableColumn<Watch, String> brandCol = new TableColumn<>("Watch Brand");
		brandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
		brandCol.setMinWidth(150);
		
		TableColumn<Watch, String> priceCol = new TableColumn<>("Watch Price");
		priceCol.setCellValueFactory(new PropertyValueFactory<>("priceFormatted"));
		priceCol.setMinWidth(150);
		
		TableColumn<Watch, String> stockCol = new TableColumn<>("Watch Stock");
		stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
		stockCol.setMinWidth(150);
		
		productTable.getColumns().addAll(watchIDCol, nameCol, brandCol, priceCol, stockCol);
		
		ArrayList<Watch> products = new ArrayList<>();
		
		String query = "SELECT * FROM Watch";
		connect.rs = connect.executeQuery(query);
		
		try {
			while (connect.rs.next()) {
				Integer watchID = connect.rs.getInt("WatchID");
				Integer brandID = connect.rs.getInt("BrandID");
				String name = connect.rs.getString("WatchName");
				Integer price = connect.rs.getInt("WatchPrice");
				Integer stock = connect.rs.getInt("WatchStock");
				
				String brand;
				
				String queryBrand = "SELECT * FROM Brand WHERE BrandID = '" + brandID + "'";
				connect2.rs = connect2.executeQuery(queryBrand);
				if (connect2.rs.next()) {
					brand = connect2.rs.getString("BrandName");
				}
				else {
					brand = "" + brandID;
				}
				
				products.add(new Watch(watchID, brand, name, price, stock));
			}
		} catch (Exception e) {}
		
		ObservableList<Watch> productItems = FXCollections.observableArrayList(products);
		productTable.setItems(productItems);
	}
	
	private void setCartTable() {
		TableColumn<CartItem, String> UserIDCol = new TableColumn<>("User ID");
		UserIDCol.setCellValueFactory(new PropertyValueFactory<>("UserIDFormatted"));
		UserIDCol.setMinWidth(300);
		
		TableColumn<CartItem, String> watchIDCol = new TableColumn<>("Watch ID");
		watchIDCol.setCellValueFactory(new PropertyValueFactory<>("watchIDFormatted"));
		watchIDCol.setMinWidth(275);
		
		
		TableColumn<CartItem, String> quantityCol = new TableColumn<>("Quantity");
		quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		quantityCol.setMinWidth(275);
		
		cartTable.getColumns().addAll(UserIDCol, watchIDCol, quantityCol);
		
		ArrayList<CartItem> cart = new ArrayList<>();
		
		String query = "SELECT * FROM Cart WHERE UserID = " + Main.currUserID;
		connect.rs = connect.executeQuery(query);
		
		try {
			while (connect.rs.next()) {
				Integer UserID = connect.rs.getInt("UserID");
				Integer watchID = connect.rs.getInt("WatchID");
				Integer quan = connect.rs.getInt("Quantity");
				
				cart.add(new CartItem(UserID, watchID, quan));
			}
		} catch (Exception e) {}
		
		ObservableList<CartItem> cartItems = FXCollections.observableArrayList(cart);
		cartTable.setItems(cartItems);
	}

	@Override
	public void setTable() {
		setProductTable();
		setCartTable();
	}

	@Override
	public void event() {		
		EventHandler<MouseEvent> rowClickedEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				TableSelectionModel<Watch> tableSelectionModel = productTable.getSelectionModel();
				tableSelectionModel.setSelectionMode(SelectionMode.SINGLE);
				ObservableList<Watch> selected = tableSelectionModel.getSelectedItems();
				
				selectedWatch = selected.get(0);
				picked.setText("Selected Watch: " + selectedWatch.getName());
			}
		};
		
		EventHandler<ActionEvent> addCartEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (selectedWatch == null) {
					new Alert(AlertType.ERROR, "You must select a watch to add first!", ButtonType.OK).showAndWait();
					return;
				}
				
				Integer quan = quantity.getValueFactory().getValue();
				if (quan == 0) {
					new Alert(AlertType.ERROR, "Quantity must be more than 0!", ButtonType.OK).showAndWait();
					return;
				}
				if (quan > selectedWatch.getStock()) {
					new Alert(AlertType.ERROR, "Quantity cannot be higher than stock!", ButtonType.OK).showAndWait();
					return;
				}
				
				String query = "INSERT INTO Cart VALUES (" + Main.currUserID + ", " + selectedWatch.getWatchID() + ", " + quan + ")";
				connect.executeUpdate(query);
				
				refreshCartTable();
			}
		};
		
		EventHandler<ActionEvent> clearCartEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure to clear cart?", ButtonType.YES, ButtonType.NO);
				ButtonType res = alert.showAndWait().orElse(ButtonType.NO);
				if (ButtonType.NO.equals(res)) return;	
				
				String query = "DELETE FROM Cart WHERE UserID = " + Main.currUserID;
				connect.executeUpdate(query);
				
				refreshCartTable();
			}
		};
		
		EventHandler<ActionEvent> checkoutEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure want to checkout?", ButtonType.YES, ButtonType.NO);
				ButtonType res = alert.showAndWait().orElse(ButtonType.NO);
				if (ButtonType.NO.equals(res)) return;	
				
				String query = "SELECT * FROM Cart";
				connect.rs = connect.executeQuery(query);
				
				try {
					if (!connect.rs.next()) {
						new Alert(AlertType.ERROR, "Cart is empty, you must fill it first!", ButtonType.OK).showAndWait();
						return;
					}
				} catch (Exception e) {};
				
				String date = new Date(System.currentTimeMillis()).toString();
				
				query = "INSERT INTO HeaderTransaction VALUES (NULL, " + Main.currUserID + ", '" + date + "')";
				connect.executeUpdate(query);
				
				
				query = "SELECT * FROM HeaderTransaction WHERE UserID = " + Main.currUserID + " ORDER BY TransactionID DESC LIMIT 1";
				connect.rs = connect.executeQuery(query);
				
				int transactionID = 0;
				try {
					if (connect.rs.next()) {
						transactionID = connect.rs.getInt("TransactionID");
					}
				} catch (Exception e) {}
				
				
				query = "SELECT * FROM Cart WHERE UserID = " + Main.currUserID;
				connect.rs = connect.executeQuery(query);
								
				try {
					while (connect.rs.next()) {
						Integer watchID = connect.rs.getInt("WatchID");
						Integer quan = connect.rs.getInt("Quantity");
						
						String queryTransaction = "INSERT INTO DetailTransaction VALUES (" + transactionID + ", " + watchID + ", " + quan + ")";
						connect2.executeUpdate(queryTransaction);
					}
				} catch (Exception e) {}
				
				query = "DELETE FROM Cart WHERE UserID = " + Main.currUserID;
				connect.executeUpdate(query);
				
				refreshCartTable();
				
				new Alert(AlertType.INFORMATION, "Checkout successful!", ButtonType.OK).showAndWait();
			}
		};
		
		productTable.setOnMouseClicked(rowClickedEvent);
		addBtn.setOnAction(addCartEvent);
		clearBtn.setOnAction(clearCartEvent);
		checkoutBtn.setOnAction(checkoutEvent);
	}

	@Override
	public void build() {
		midContainer.getChildren().addAll(quantityLbl, quantity, addBtn);
		
		bottomContainer.getChildren().addAll(clearBtn, checkoutBtn);
		
		this.getChildren().addAll(productTable, picked, midContainer, cartTable, bottomContainer);
	}
	
	private void refreshCartTable() {
		this.getChildren().remove(cartTable);
		cartTable = new TableView<>();
		setCartTable();
		event();
		this.getChildren().remove(bottomContainer);
		this.getChildren().addAll(cartTable, bottomContainer);
		quantity.getValueFactory().setValue(0);
	}
}
