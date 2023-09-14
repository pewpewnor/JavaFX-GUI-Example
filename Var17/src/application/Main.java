package application;
	
import Page.LoginPage;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	public static Stage mainStage;
	public static String currUserRole = "Admin";
	public static Integer currUserID = 2;
	
	@Override
	public void start(Stage primaryStage) {
		Main.mainStage = primaryStage;
		new LoginPage();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
