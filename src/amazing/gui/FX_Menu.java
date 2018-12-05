package amazing.gui;

import java.io.File;
import java.net.URL;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;

public class FX_Menu extends Application { //Class that checks the menu on JavaFX

	@Override
	public void start(Stage stage) throws Exception { //Method to start the menu
		File location_fx = new File("src/amazing/gui/menu.fxml");
		URL fx_file = (location_fx.toURI().toURL());
		Parent root = FXMLLoader.load(fx_file);
		Scene menu = new Scene(root);
		stage.setScene(menu);
		stage.show();
	}

}
