package InternalPage;

import application.Connect;
import application.Connect2;
import application.Connect3;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

public abstract class InternalPage extends VBox {
	protected Scene scene;
	protected Connect connect = Connect.getInstance();
	protected Connect2 connect2 = Connect2.getInstance();
	protected Connect3 connect3 = Connect3.getInstance();
	
	public InternalPage() {
		loadPage();
	}
	
	public void loadPage() {
		init();
		set();
		setTable();
		event();
		build();
	}
	
	public abstract void init();
	public abstract void set();
	public abstract void setTable();
	public abstract void event();
	public abstract void build();
}
