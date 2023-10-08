package InternalPage;

import java.util.ArrayList;

import TableItem.BrandItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ManageBrandInternal extends InternalPage {
	HBox row1, bottomContainer;
	
	TableView<BrandItem> brandTable;
	
	TextField nameInput;
	
	Label nameLbl;
	
	Button insertBtn, updateBtn, deleteBtn;
	
	Integer selectedBrandID = null;

	@Override
	public void init() {
		row1 = new HBox();
		bottomContainer = new HBox();
		
		brandTable = new TableView<>();
		
		nameInput = new TextField();
		
		nameLbl = new Label("Brand Name: ");
		
		insertBtn = new Button("Insert Brand");
		updateBtn = new Button("Update Brand");
		deleteBtn = new Button("Delete Brand");
	}

	@Override
	public void set() {
		this.setPadding(new Insets(10));
		this.setSpacing(20);
		
		row1.setSpacing(20);
		bottomContainer.setSpacing(20);
		
		bottomContainer.setAlignment(Pos.CENTER);
		
		nameInput.setPromptText("Brand Name");
		
		Font lblFont = Font.font("", FontWeight.BOLD, 20);
		nameLbl.setFont(lblFont);
	}

	@Override
	public void setTable() {
		TableColumn<BrandItem, String> brandIDCol = new TableColumn<>("Brand ID");
		brandIDCol.setCellValueFactory(new PropertyValueFactory<>("brandIDFormatted"));
		brandIDCol.setMinWidth(150);
		
		TableColumn<BrandItem, String> brandNameCol = new TableColumn<>("Brand Name");
		brandNameCol.setCellValueFactory(new PropertyValueFactory<>("brandName"));
		brandNameCol.setMinWidth(350);
		
		brandTable.getColumns().addAll(brandIDCol, brandNameCol);
		
		ArrayList<BrandItem> brandArray = new ArrayList<>();
		
		String query = "SELECT * FROM Brand";
		connect.rs = connect.executeQuery(query);
		
		try {
			while (connect.rs.next()) {
				Integer brandID = connect.rs.getInt("BrandID");
				String brandName = connect.rs.getString("BrandName");
				
				brandArray.add(new BrandItem(brandID, brandName));
			}
		} catch (Exception e) {}
		
		ObservableList<BrandItem> brandItems = FXCollections.observableArrayList(brandArray);
		brandTable.setItems(brandItems);
	}

	@Override
	public void event() {
		EventHandler<MouseEvent> rowClickedEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				TableSelectionModel<BrandItem> tableSelectionModel = brandTable.getSelectionModel();
				tableSelectionModel.setSelectionMode(SelectionMode.SINGLE);
				ObservableList<BrandItem> selected = tableSelectionModel.getSelectedItems();
				
				BrandItem selectedBrand = selected.get(0);
				selectedBrandID = selectedBrand.getBrandID();
				nameInput.setText(selectedBrand.getBrandName());
			}
		};
		
		EventHandler<ActionEvent> insertEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (nameInput.getText().trim().isEmpty()) {
					new Alert(AlertType.ERROR, "Brand name must be filled!", ButtonType.OK).showAndWait();
					return;
				}
				
				String brandName = nameInput.getText();
				
				String query = "INSERT INTO Brand VALUES (NULL, '" + brandName + "')";
				connect.executeUpdate(query);
				
				refreshBrandTable();
				new Alert(AlertType.INFORMATION, "New brand successfully inserted!", ButtonType.OK).showAndWait();
			}
		};
		
		EventHandler<ActionEvent> updateEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (selectedBrandID == null) {
					new Alert(AlertType.ERROR, "You must select a brand from the table first!", ButtonType.OK).showAndWait();
					return;
				}
				if (nameInput.getText().trim().isEmpty()) {
					new Alert(AlertType.ERROR, "Brand name must be filled!", ButtonType.OK).showAndWait();
					return;
				}
				
				String brandName = nameInput.getText();
				
				
				String query = "UPDATE Brand SET BrandName = '" + brandName  + "' WHERE BrandID = " + selectedBrandID;
				try {
					connect.executeUpdate(query);
				} catch (Exception e) {
					new Alert(AlertType.ERROR, "Brand name must be unique!", ButtonType.OK).showAndWait();
					return;
				}
				
				refreshBrandTable();
				new Alert(AlertType.INFORMATION, "Brand successfully updated!", ButtonType.OK).showAndWait();
			}
		};
		
		EventHandler<ActionEvent> deleteEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (selectedBrandID == null) {
					new Alert(AlertType.ERROR, "You must select a brand from the table first!", ButtonType.OK).showAndWait();
					return;
				}
				
				String query = "DELETE FROM Brand WHERE BrandID = " + selectedBrandID;
				try {
					connect.executeUpdate(query);
				} catch (Exception e) {
					new Alert(AlertType.ERROR, "Brand name must be unique!", ButtonType.OK).showAndWait();
					return;
				}
				
				refreshBrandTable();
				new Alert(AlertType.INFORMATION, "Brand successfully deleted!", ButtonType.OK).showAndWait();
			}
		};
		
		brandTable.setOnMouseClicked(rowClickedEvent);
		insertBtn.setOnAction(insertEvent);
		updateBtn.setOnAction(updateEvent);
		deleteBtn.setOnAction(deleteEvent);
	}

	@Override
	public void build() {
		row1.getChildren().addAll(nameLbl, nameInput);
		bottomContainer.getChildren().addAll(insertBtn, updateBtn, deleteBtn);
		
		this.getChildren().addAll(brandTable, row1, bottomContainer);
	}
	
	private void refreshBrandTable() {
		this.getChildren().remove(brandTable);
		brandTable = new TableView<>();
		setTable();
		event();
		this.getChildren().removeAll(row1, bottomContainer);
		this.getChildren().add(brandTable);
		this.getChildren().addAll(row1, bottomContainer);
		nameInput.setText("");
	}
}
