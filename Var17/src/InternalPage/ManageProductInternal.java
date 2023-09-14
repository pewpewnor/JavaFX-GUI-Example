package InternalPage;

import java.sql.SQLException;
import java.util.ArrayList;

import TableItem.Watch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ManageProductInternal extends InternalPage {
	HBox row11, row12, row21, row22, row1, row2, bottomContainer;
	
	TableView<Watch> productTable;
	
	TextField nameInput, priceInput;
	ComboBox<String> brandInput;
	Spinner<Integer> stockInput;
	
	Label nameLbl, priceLbl, stockLbl, brandLbl;
	
	Button insertBtn, updateBtn, deleteBtn;
	
	Integer selectedWatchID = null;
	
	@Override
	public void init() {
		row1 = new HBox();
		row11 = new HBox();
		row12 = new HBox();
		row2 = new HBox();
		row21 = new HBox();
		row22 = new HBox();
		bottomContainer = new HBox();
		
		productTable = new TableView<>();
		
		nameInput = new TextField();
		priceInput = new TextField();
		stockInput = new Spinner<>(0, 99, 0);
		
		ArrayList<String> brands = new ArrayList<>();
		String query = "SELECT * FROM Brand";
		connect.rs = connect.executeQuery(query);
		try {
			while (connect.rs.next()) {
				String brandName = connect.rs.getString("BrandName");
				brands.add(brandName);
			}
		} catch (Exception e) {}
		brandInput = new ComboBox<String>(FXCollections.observableArrayList(brands));
		brandInput.setValue("Choose One");
		
		nameLbl = new Label("Watch Name: ");
		priceLbl = new Label("Watch Price: ");
		stockLbl = new Label("Watch Stock: ");
		brandLbl = new Label("Watch Brand: ");
		
		insertBtn = new Button("Insert Watch");
		updateBtn = new Button("Update Watch");
		deleteBtn = new Button("Delete Watch");
	}

	@Override
	public void set() {
		this.setPadding(new Insets(10));
		this.setSpacing(20);
		
		row11.setSpacing(20);
		row12.setSpacing(20);
		row21.setSpacing(20);
		row22.setSpacing(20);
		bottomContainer.setSpacing(20);
		
		row1.setSpacing(20);
		row2.setSpacing(20);
		
		row11.setAlignment(Pos.CENTER_LEFT);
		row12.setAlignment(Pos.CENTER_RIGHT);
		row21.setAlignment(Pos.CENTER_LEFT);
		row22.setAlignment(Pos.CENTER_RIGHT);
		bottomContainer.setAlignment(Pos.CENTER);
		
		nameInput.setPromptText("Name");
		priceInput.setPromptText("Price");
		stockInput.setPromptText("Stock");
		
		Font lblFont = Font.font("", FontWeight.BOLD, 20);
		nameLbl.setFont(lblFont);
		priceLbl.setFont(lblFont);
		stockLbl.setFont(lblFont);
		brandLbl.setFont(lblFont);
	}

	@Override
	public void setTable() {
		TableColumn<Watch, String> watchIDCol = new TableColumn<>("Watch ID");
		watchIDCol.setCellValueFactory(new PropertyValueFactory<>("watchIDFormatted"));
		watchIDCol.setMinWidth(100);
		
		TableColumn<Watch, String> nameCol = new TableColumn<>("Watch Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		nameCol.setMinWidth(300);
		
		TableColumn<Watch, String> brandCol = new TableColumn<>("Watch Brand");
		brandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
		brandCol.setMinWidth(150);
		
		TableColumn<Watch, String> priceCol = new TableColumn<>("Watch Price");
		priceCol.setCellValueFactory(new PropertyValueFactory<>("priceFormatted"));
		priceCol.setMinWidth(125);
		
		TableColumn<Watch, String> stockCol = new TableColumn<>("Watch Stock");
		stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
		stockCol.setMinWidth(125);
		
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

	@Override
	public void event() {
		EventHandler<MouseEvent> rowClickedEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				TableSelectionModel<Watch> tableSelectionModel = productTable.getSelectionModel();
				tableSelectionModel.setSelectionMode(SelectionMode.SINGLE);
				ObservableList<Watch> selected = tableSelectionModel.getSelectedItems();
				
				Watch selectedWatch = selected.get(0);
				
				selectedWatchID = selectedWatch.getWatchID();
				nameInput.setText(selectedWatch.getName());
				priceInput.setText(""+selectedWatch.getPrice());
				brandInput.setValue(selectedWatch.getBrand());
				stockInput.getValueFactory().setValue(selectedWatch.getStock());
			}
		};
		
		EventHandler<ActionEvent> insertEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String name = nameInput.getText();
				if (name.endsWith(" Watch") == false) {
					new Alert(AlertType.ERROR, "Watch Name must ends with ' Watch'!", ButtonType.OK).showAndWait();
					return;
				}
				Integer stock = stockInput.getValue();
				if (stock == 0) {
					new Alert(AlertType.ERROR, "Watch Stock must be more than 0!", ButtonType.OK).showAndWait();
					return;
				}
				Integer price = convertToInt(priceInput.getText());
				if (price == null || price <= 0) {
					new Alert(AlertType.ERROR, "Watch Price must be higher than 0!", ButtonType.OK).showAndWait();
					return;
				}
				
				if (brandInput.getSelectionModel().getSelectedItem() == "Choose One") {
					new Alert(AlertType.ERROR, "Watch Brand must be chosen!", ButtonType.OK).showAndWait();
					return;
				}
				String brand = brandInput.getSelectionModel().getSelectedItem();
				
				
				String query = "SELECT * FROM Brand WHERE BrandName = '" + brand + "'"; 
				connect.rs = connect.executeQuery(query);
				try {
					connect.rs.next();
					Integer brandID = connect.rs.getInt("BrandID");
					query = "INSERT INTO Watch VALUES (NULL, " + brandID + ", '" + name + "', " + price + ", " + stock + ")";
					connect.executeUpdate(query);
				} catch (SQLException e) {
				}
				
				refreshProductTable();
				refreshSelected();
				new Alert(AlertType.INFORMATION, "New watch successfully inserted!", ButtonType.OK).showAndWait();
			}
		};
		
		EventHandler<ActionEvent> updateEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (selectedWatchID == null) {
					new Alert(AlertType.ERROR, "You must select a watch from the table first!", ButtonType.OK).showAndWait();
					return;
				}
				String name = nameInput.getText();
				if (name.endsWith(" Watch") == false) {
					new Alert(AlertType.ERROR, "Watch Name must ends with ' Watch'!", ButtonType.OK).showAndWait();
					return;
				}
				Integer stock = stockInput.getValue();
				if (stock == 0) {
					new Alert(AlertType.ERROR, "Watch Stock must be more than 0!", ButtonType.OK).showAndWait();
					return;
				}
				Integer price = convertToInt(priceInput.getText());
				if (price == null || price <= 0) {
					new Alert(AlertType.ERROR, "Watch Price must be higher than 0!", ButtonType.OK).showAndWait();
					return;
				}
				if (brandInput.getSelectionModel().getSelectedItem() == "Choose One") {
					new Alert(AlertType.ERROR, "Watch Brand must be chosen!", ButtonType.OK).showAndWait();
					return;
				}
				String brand = brandInput.getSelectionModel().getSelectedItem();
				
				String query = "SELECT * FROM Brand WHERE BrandName = '" + brand + "'"; 
				connect.rs = connect.executeQuery(query);
				
				Integer brandID = 1;
				try {
					connect.rs.next();
					brandID = connect.rs.getInt("BrandID");
				} catch (SQLException e) {}
				
				query = "UPDATE Watch SET BrandID = " + brandID + ", WatchName = '" + name + "', WatchPrice = " + price + ", WatchStock = " + stock + " WHERE WatchID = " + selectedWatchID;
				connect.executeUpdate(query);
				
				refreshProductTable();
				refreshSelected();
				new Alert(AlertType.INFORMATION, "Watch successfully updated!", ButtonType.OK).showAndWait();
			}
		};
		
		EventHandler<ActionEvent> deleteEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (selectedWatchID == null) {
					new Alert(AlertType.ERROR, "You must select a watch from the table first!", ButtonType.OK).showAndWait();
					return;
				}
				
				String query = "DELETE FROM Watch WHERE WatchID = " + selectedWatchID;
				connect.executeUpdate(query);
				
				refreshProductTable();
				refreshSelected();
				new Alert(AlertType.INFORMATION, "Watch successfully deleted!", ButtonType.OK).showAndWait();
			}
		};
		
		productTable.setOnMouseClicked(rowClickedEvent);
		insertBtn.setOnAction(insertEvent);
		updateBtn.setOnAction(updateEvent);
		deleteBtn.setOnAction(deleteEvent);
	}
	
	@Override
	public void build() {
		row11.getChildren().addAll(nameLbl, nameInput);
		row12.getChildren().addAll(priceLbl, priceInput);
		row1.getChildren().addAll(row11, row12);
		
		row21.getChildren().addAll(stockLbl, stockInput);
		row22.getChildren().addAll(brandLbl, brandInput);
		row2.getChildren().addAll(row21, row22);
		
		bottomContainer.getChildren().addAll(insertBtn, updateBtn, deleteBtn);
		
		this.getChildren().addAll(productTable, row1, row2, bottomContainer);
	}

	private Integer convertToInt(String str) {
		Integer n = null;
		try {
			n = Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return null;
		}
		return n;
	}

	private void refreshProductTable() {
		this.getChildren().remove(productTable);
		productTable = new TableView<>();
		setTable();
		event();
		this.getChildren().removeAll(row1, row2, bottomContainer);
		this.getChildren().add(productTable);
		this.getChildren().addAll(row1, row2, bottomContainer);
	}
	
	private void refreshSelected() {
		selectedWatchID = null;
		nameInput.setText("");
		priceInput.setText("");
		stockInput.getValueFactory().setValue(0);
		brandInput.setValue("Choose One");
	}
}
