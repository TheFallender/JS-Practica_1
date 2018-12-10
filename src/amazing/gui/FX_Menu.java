package amazing.gui;

import amazing.amazing.Amazing;
import javafx.event.EventHandler;
import javafx.stage.*;

public class FX_Menu {

	public static int menu_get(int menu_a) {
		return menu_a;
	}

	
	public static void close_call (Stage main) {
		main.setOnCloseRequest(new EventHandler<WindowEvent>() 
			{
			public void handle(WindowEvent we) {
				Amazing.set_menu(0, 6);
			}
			}
		); 
	}
}
