package Page;

import InternalPage.*;
import application.Main;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import jfxtras.labs.scene.control.window.CloseIcon;
import jfxtras.labs.scene.control.window.Window;

public class MainPage extends Page {
	BorderPane mainContainer;
	
	MenuBar menuBar;
	Menu transaction, management, usermenu;
	MenuItem buyWatch, myTransactionHistory, manageProduct, manageBrand, logout;

	@Override
	public void init() {
		mainContainer = new BorderPane();
		scene = new Scene(mainContainer, 1400, 800);
		
		menuBar = new MenuBar();
		
		transaction = new Menu("Transaction");
		management = new Menu("Management");
		usermenu = new Menu("User");
		
		buyWatch = new MenuItem("Buy Watch");
		myTransactionHistory = new MenuItem("My Transaction History");
		manageProduct = new MenuItem("Manage Product");
		manageBrand = new MenuItem("Manage Brand");
		logout = new MenuItem("Logout");
	}

	@Override
	public void set() {
		usermenu.getItems().add(logout);
		menuBar.getMenus().add(usermenu);
		
		if (Main.currUserRole.equals("Customer")) {
			transaction.getItems().add(buyWatch);
			transaction.getItems().add(myTransactionHistory);
			menuBar.getMenus().add(transaction);
		}
		else {
			management.getItems().add(manageProduct);
			management.getItems().add(manageBrand);
			menuBar.getMenus().add(management);			
		}
	}

	@Override
	public void style() {
		mainContainer.setStyle("-fx-background-color: gray;");
	}

	@Override
	public void event() {
		EventHandler<ActionEvent> buyProductEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				createWindow("Buy Product", 850, 700, new BuyProductInternal());
			}
		};
		
		EventHandler<ActionEvent> transactionHistoryEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				createWindow("View Transaction History", 1100, 700, new TransactionHistoryInternal());
			}
		};
		
		EventHandler<ActionEvent> manageProductEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				createWindow("Manage Product", 820, 650, new ManageProductInternal());
			}
		};
		
		EventHandler<ActionEvent> manageBrandEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				createWindow("Manage Brand", 500, 600, new ManageBrandInternal());
			}
		};
		
		EventHandler<ActionEvent> logoutEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				new LoginPage();
			}
		};
		
		buyWatch.setOnAction(buyProductEvent);
		myTransactionHistory.setOnAction(transactionHistoryEvent);
		manageProduct.setOnAction(manageProductEvent);
		manageBrand.setOnAction(manageBrandEvent);
		logout.setOnAction(logoutEvent);
	}

	@Override
	public void build() {
		mainContainer.setTop(menuBar);
		
		Main.mainStage.setTitle("Main Page");
	}
	
	private void createWindow(String title, int width, int height, Node node) {
		Window window = new Window("hello");
		
//		window.getLeftIcons().add(new MinimizeIcon(window));
		window.getRightIcons().add(new CloseIcon(window));
		
		window.setPrefSize(width, height);
		window.setLayoutX(scene.getWidth() / 2 - width / 2);
		window.setLayoutY(scene.getHeight() / 2 - height / 2);
		
		window.setTitle(title);
		window.getContentPane().getChildren().add(node);
		
		mainContainer.getChildren().add(window);
	}

}
