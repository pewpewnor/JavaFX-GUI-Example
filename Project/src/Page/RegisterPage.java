package Page;

import application.Main;
import application.Validator;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

public class RegisterPage extends Page {

	BorderPane mainContainer;
	GridPane formContainer, radioContainer;
	
	TextField email, name;
	PasswordField password, confirm;
	
	ToggleGroup genderGroup;
	RadioButton male, female;
	
	Label titleLbl, nameLbl, emailLbl, genderLbl, passwordLbl, confirmLbl;
	
	Button loginBtn, registerBtn;

	@Override
	public void init() {
		mainContainer = new BorderPane();
		formContainer = new GridPane();
		radioContainer = new GridPane();
		scene = new Scene(mainContainer, 720, 780);
		
		email = new TextField();
		name = new TextField();
		password = new PasswordField();
		confirm = new PasswordField();
		
		genderGroup = new ToggleGroup();
		male = new RadioButton("Male");
		female = new RadioButton("Female");
		
		titleLbl = new Label("Register");
		nameLbl = new Label("Name : ");
		emailLbl = new Label("Email :");
		genderLbl = new Label("Gender :");
		passwordLbl = new Label("Password :");
		confirmLbl = new Label("Confirm Password:");
		
		registerBtn = new Button("Register");
		loginBtn = new Button("Back to Login");
	}

	@Override
	public void set() {
		mainContainer.setPadding(new Insets(50, 100, 50, 100));
		formContainer.setAlignment(Pos.CENTER);
		formContainer.setPadding(new Insets(20));
		formContainer.setVgap(22);
		formContainer.setHgap(10);
		radioContainer.setHgap(20);
		
		name.setPromptText("Name");
		email.setPromptText("Email Address");
		password.setPromptText("Password");
		confirm.setPromptText("Confirm Password");
		
		male.setToggleGroup(genderGroup);
		female.setToggleGroup(genderGroup);
		
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
		EventHandler<ActionEvent> registerEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String iname = name.getText().trim();
				if (iname.length() < 5 || iname.length() > 40) {
					new Alert(AlertType.ERROR, "Name must be between 5 - 40 characters!", ButtonType.OK).showAndWait();
					return;
				}
				
				String igender;
				RadioButton selected = (RadioButton) genderGroup.getSelectedToggle();
				if (selected == null) {
					new Alert(AlertType.ERROR, "Gender must be chosen!", ButtonType.OK).showAndWait();
					return;
				}
				else {
					igender = selected.getText();	
				}
				
				String iemail = email.getText();
				if (Validator.emailFormat(iemail) == false) {
					new Alert(AlertType.ERROR, "Email format invalid!", ButtonType.OK).showAndWait();
					return;
				}
				
				String ipassword = password.getText();
				if (ipassword.length() < 6 || ipassword.length() > 20) {
					new Alert(AlertType.ERROR, "Password must be between 6 - 20 characters!", ButtonType.OK).showAndWait();
					return;
				}
				if (Validator.alphanumeric(ipassword) == false) {
					new Alert(AlertType.ERROR, "Password must be alphanumeric!", ButtonType.OK).showAndWait();
					return;
				}
				
				String iconfirm = confirm.getText();
				if (iconfirm.equals(ipassword) == false) {
					new Alert(AlertType.ERROR, "Confirm password must be same as password!", ButtonType.OK).showAndWait();
					return;
				}
				
				String irole = "Customer";
				
				String query = "INSERT INTO User VALUES (NULL, '" + iname + "', '" + iemail + "', '" + ipassword + "', '" + igender + "', '" + irole + "')";
				connect.executeUpdate(query);
				
				new Alert(AlertType.INFORMATION, "Register successful!", ButtonType.OK).showAndWait();
				
				new LoginPage();
			}
		};
		
		EventHandler<ActionEvent> loginEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				new LoginPage();
			}
		};
		
		registerBtn.setOnAction(registerEvent);
		loginBtn.setOnAction(loginEvent);
	}

	@Override
	public void build() {
		radioContainer.add(male, 0, 0);
		radioContainer.add(female, 1, 0);
		
		formContainer.add(titleLbl, 0, 0);
		formContainer.add(nameLbl, 0, 1);
		formContainer.add(name, 0, 2);
		formContainer.add(genderLbl, 0, 3);
		formContainer.add(radioContainer, 0, 4);
		formContainer.add(emailLbl, 0, 5);
		formContainer.add(email, 0, 6);
		formContainer.add(passwordLbl, 0, 7);
		formContainer.add(password, 0, 8);
		formContainer.add(confirmLbl, 0, 9);
		formContainer.add(confirm, 0, 10);
		formContainer.add(registerBtn, 0, 11);
		formContainer.add(loginBtn, 0, 12);
		
		mainContainer.setCenter(formContainer);
		
		Main.mainStage.setTitle("Register");
	}
}
