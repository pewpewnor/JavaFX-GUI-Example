package InternalPage;

import java.util.ArrayList;

import TableItem.DetailItem;
import TableItem.HeaderItem;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class TransactionHistoryInternal extends InternalPage {
	
	TableView<HeaderItem> headerTable;
	TableView<DetailItem> detailTable;
	
	Label picked;
	
	Integer selectedTransactionID = null;

	@Override
	public void init() {
		headerTable = new TableView<>();
		detailTable = new TableView<>();
		
		picked = new Label("Selected Transaction: None");
	}

	@Override
	public void set() {
		this.setPadding(new Insets(10));
		this.setSpacing(10);
	}
	
	private void setHeaderTable() {
		TableColumn<HeaderItem, String> transactionIDCol = createHeaderTableCollumn("Transaction ID", "transactionIDFormatted", 375);
		TableColumn<HeaderItem, String> UserIDCol = createHeaderTableCollumn("User ID", "UserIDFormatted", 350);
		TableColumn<HeaderItem, String> transactionDateCol = createHeaderTableCollumn("Transaction Date", "transactionDate", 375);
		
		headerTable.getColumns().addAll(transactionIDCol, UserIDCol, transactionDateCol);
		
		ArrayList<HeaderItem> headerArray = new ArrayList<>();
		
		String query = "SELECT * FROM HeaderTransaction WHERE UserID = " + Main.currUserID;
		connect.rs = connect.executeQuery(query);
		
		try {
			while (connect.rs.next()) {
				Integer transactionID = connect.rs.getInt("TransactionID");
				Integer UserID = connect.rs.getInt("UserID");
				String transactionDate = connect.rs.getDate("TransactionDate").toString();
				
				headerArray.add(new HeaderItem(transactionID, UserID, transactionDate));
			}
		} catch (Exception e) {}
		
		ObservableList<HeaderItem> headerItems = FXCollections.observableArrayList(headerArray);
		headerTable.setItems(headerItems);
	}
	
	private void fillDetail() {
		Integer transactionID = selectedTransactionID;
		
		ArrayList<DetailItem> detailArray = new ArrayList<>();
		
		String query = "SELECT * FROM DetailTransaction WHERE TransactionID = " + transactionID;
		connect.rs = connect.executeQuery(query);
		
		try {
			while (connect.rs.next()) {
				Integer watchID = connect.rs.getInt("WatchID");
				
				String query2 = "SELECT * FROM Watch WHERE WatchID = " + watchID;
				connect2.rs = connect2.executeQuery(query2);
				connect2.rs.next();
				Integer watchPrice = connect2.rs.getInt("WatchPrice");
				String watchName = connect2.rs.getString("WatchName");
				
				String query3 = "SELECT * FROM Brand WHERE BrandID = " + connect2.rs.getInt("BrandID");
				connect3.rs = connect3.executeQuery(query3);
				connect3.rs.next();
				String watchBrand = connect3.rs.getString("BrandName");
				
				
				Integer quantity = connect.rs.getInt("Quantity");
				
				
				detailArray.add(new DetailItem(transactionID, watchID, watchName, watchBrand, watchPrice, quantity));
			}
		} catch (Exception e) {}
		
		ObservableList<DetailItem> detailItems = FXCollections.observableArrayList(detailArray);
		detailTable.setItems(detailItems);
	}
	
	private void setDetailTable() {
		TableColumn<DetailItem, String> transactionIDCol = createDetailTableCollumn("Transaction ID", "transactionIDFormatted", 200);
		TableColumn<DetailItem, String> watchIDCol = createDetailTableCollumn("Watch ID", "watchIDFormatted", 100);
		TableColumn<DetailItem, String> watchNameCol = createDetailTableCollumn("Watch Name", "watchName", 275);
		TableColumn<DetailItem, String> watchBrandCol = createDetailTableCollumn("Watch Brand", "watchBrand", 150);
		TableColumn<DetailItem, String> watchPriceCol = createDetailTableCollumn("Watch Price", "watchPriceFormatted", 150);
		TableColumn<DetailItem, String> quantityCol = createDetailTableCollumn("Quantity", "quantity", 100);
		TableColumn<DetailItem, String> subTotalCol = createDetailTableCollumn("Sub Total", "subTotal", 100);
		
		detailTable.getColumns().addAll(transactionIDCol, watchIDCol, watchNameCol, watchBrandCol, watchPriceCol, quantityCol, subTotalCol);
	}

	@Override
	public void setTable() {
		setHeaderTable();
		setDetailTable();
	}

	@Override
	public void event() {
		EventHandler<MouseEvent> rowClickedEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				TableSelectionModel<HeaderItem> tableSelectionModel = headerTable.getSelectionModel();
				tableSelectionModel.setSelectionMode(SelectionMode.SINGLE);
				ObservableList<HeaderItem> selected = tableSelectionModel.getSelectedItems();
				
				selectedTransactionID = selected.get(0).getTransactionID();
				picked.setText("Selected Transaction: Transaction " + selectedTransactionID);
				
				refreshDetailTable();
			}
		};
		
		headerTable.setOnMouseClicked(rowClickedEvent);
	}

	@Override
	public void build() {
		this.getChildren().addAll(headerTable, picked, detailTable);
	}
	
	private TableColumn<HeaderItem, String> createHeaderTableCollumn(String colName, String getName, int minWidth) {
		TableColumn<HeaderItem, String> col = new TableColumn<>(colName);
		col.setCellValueFactory(new PropertyValueFactory<>(getName));
		col.setMinWidth(minWidth);
		return col;
	}
	
	private TableColumn<DetailItem, String> createDetailTableCollumn(String colName, String getName, int minWidth) {
		TableColumn<DetailItem, String> col = new TableColumn<>(colName);
		col.setCellValueFactory(new PropertyValueFactory<>(getName));
		col.setMinWidth(minWidth);
		return col;
	}
	
	private void refreshDetailTable() {
		this.getChildren().remove(detailTable);
		detailTable = new TableView<>();
		setDetailTable();
		fillDetail();
		event();
		this.getChildren().add(detailTable);
	}
}
