package org.gruppe2.ui.javafx;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

/**
 * Created by kjors on 04.04.2016.
 */
public class BorderPaneController extends BorderPane {

	BorderPaneController() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
					"/views/" + getClass().getSimpleName() + ".fxml"));

			if (fxmlLoader.getController() == null) {
				fxmlLoader.setController(this);
			}

			if (fxmlLoader.getRoot() == null) {
				fxmlLoader.setRoot(this);
			}

			this.setStyle("-fx-background-color: black");
			if (fxmlLoader.getRoot() == null) {
				fxmlLoader.load();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
