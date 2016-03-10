package org.gruppe2.frontend;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.GridPane;

public class TopMenu {

	public TopMenu(GridPane grid, GUI gui) {
		
		setMainMenu(grid, gui);

	}

	private void setMainMenu(GridPane grid, GUI gui) {
		// Menus
		MenuBar menuBar = new MenuBar();
		Menu menuNewGame = new Menu("new");
		Menu menuSettings = new Menu("Settings");

		// Add to scene
		menuBar.getMenus().addAll(menuNewGame, menuSettings);
		grid.getChildren().add(0, menuBar);

		setEventHandlers(gui, menuSettings, menuNewGame);
	}

	private void setEventHandlers(GUI gui, Menu menuSettings, Menu menuNewGame) {
		// Covalent bonds
				CheckMenuItem menuNewOfflineGame = new CheckMenuItem("new offline game");
				
				menuNewOfflineGame.selectedProperty().addListener(new ChangeListener<Boolean>() {
					public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
						gui.pokerGame.startOfflineGame();
					}
				});
				CheckMenuItem menuNewOnlineGame = new CheckMenuItem("new online game");
				menuNewOnlineGame.selectedProperty().addListener(new ChangeListener<Boolean>() {
					public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
						gui.pokerGame.startOnlineGame();
					}
				});

				

				// Add to menu
				menuNewGame.getItems().addAll(menuNewOfflineGame, menuNewOnlineGame);
	}

}
