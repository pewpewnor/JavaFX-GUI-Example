package Page;

import application.Connect;
import application.Main;
import javafx.scene.Scene;

public abstract class Page {
	protected Scene scene;
	protected Connect connect = Connect.getInstance();
	
	public Page() {
		loadPage();
		Main.mainStage.setScene(scene);
		Main.mainStage.show();
	}
	
	public void loadPage() {
		init();
		set();
		style();
		event();
		build();
	}
	
	public abstract void init();
	public abstract void set();
	public abstract void style();
	public abstract void event();
	public abstract void build();
}
