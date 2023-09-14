package Page;

import application.Main;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

public class LoginPage extends Page {
	
	BorderPane mainContainer;
	GridPane formContainer;
	
	TextField email;
	PasswordField password;
	
	Label titleLbl, emailLbl, passwordLbl;
	
	Button loginBtn, registerBtn;

	@Override
	public void init() {
		mainContainer = new BorderPane();
		formContainer = new GridPane();
		scene = new Scene(mainContainer, 720, 720);
		
		email = new TextField();
		password = new PasswordField();
		
		titleLbl = new Label("Watches Dealer Login");
		emailLbl = new Label("Email :");
		passwordLbl = new Label("Password :");
		
		loginBtn = new Button("Login");
		registerBtn = new Button("Register Instead");
	}

	@Override
	public void set() {
		mainContainer.setPadding(new Insets(150));
		formContainer.setAlignment(Pos.CENTER);
		formContainer.setPadding(new Insets(20));
		formContainer.setVgap(22);
		formContainer.setHgap(10);
		
		email.setPromptText("Email Address");
		password.setPromptText("Password");
		
		GridPane.setHalignment(titleLbl, HPos.CENTER);
		
		loginBtn.setMinWidth(300);
		registerBtn.setMinWidth(300);
	}

	@Override
	public void style() {
		mainContainer.setStyle("-fx-background-color: lightcyan;");
		formContainer.setStyle("-fx-background-color: white;");
		
		titleLbl.setFont(new Font(24));
		titleLbl.setStyle("-fx-font-weight: bold;");
		titleLbl.setTextFill(Color.BLACK);
		
		Font buttonFont = Font.font("", FontWeight.BOLD, 16);
		loginBtn.setTextFill(Color.WHITE);
		loginBtn.setFont(buttonFont);
		loginBtn.setStyle("-fx-background-color: black;");
		registerBtn.setTextFill(Color.WHITE);
		registerBtn.setFont(buttonFont);
		registerBtn.setStyle("-fx-background-color: black;");
	}
	
	@Override
	public void event() {
		EventHandler<ActionEvent> loginEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String iemail = email.getText();
				String ipassword = password.getText();
				
				if (iemail.trim().isEmpty() || ipassword.trim().isEmpty()) {
					new Alert(AlertType.ERROR, "Email and password must be filled!", ButtonType.OK).showAndWait();
					return;
				}
				
				String query = "SELECT * FROM User WHERE UserEmail = '" + iemail + "' AND UserPassword = '" + ipassword + "'";
				connect.rs = connect.executeQuery(query);
				
				try {
					if (connect.rs.next()) {
						Integer UserID = connect.rs.getInt("UserID");
						String role = connect.rs.getString("UserRole");
						
						Main.currUserID = UserID;
						Main.currUserRole = role;
						
						connect.rs = null;
					}
					else {
						new Alert(AlertType.ERROR, "Invalid credential!", ButtonType.OK).showAndWait();
						return;
					}
				} catch (Exception e) {}
				
				new MainPage();
			}
		};
		
		EventHandler<ActionEvent> registerEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				new RegisterPage();
			}
		};
		
		loginBtn.setOnAction(loginEvent);
		registerBtn.setOnAction(registerEvent);
	}

	@Override
	public void build() {
		formContainer.add(titleLbl, 0, 0);
		formContainer.add(emailLbl, 0, 1);
		formContainer.add(email, 0, 2);
		formContainer.add(passwordLbl, 0, 3);
		formContainer.add(password, 0, 4);
		formContainer.add(loginBtn, 0, 5);
		formContainer.add(registerBtn, 0, 6);
		
		mainContainer.setCenter(formContainer);
		
		Main.mainStage.setTitle("Login");
	}
	
}
