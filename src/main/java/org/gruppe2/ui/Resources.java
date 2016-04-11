package org.gruppe2.ui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public class Resources {
	public static void loadFXML(Node node){
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(Resources.class.getResource("/views/" + node.getClass().getSimpleName().replaceFirst("Controller", "") + ".fxml"));
			
			fxmlLoader.setRoot(node);
			fxmlLoader.setController(node);
			
			fxmlLoader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
