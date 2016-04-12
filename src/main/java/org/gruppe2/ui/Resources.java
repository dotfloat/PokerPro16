package org.gruppe2.ui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public class Resources {
    public static void loadFXML(Node node) {
        try {
            String name = "/views/" + node.getClass().getSimpleName() + ".fxml";

            System.out.println(name);

            FXMLLoader fxmlLoader = new FXMLLoader(Resources.class.getResource(name));

            fxmlLoader.setRoot(node);
            fxmlLoader.setController(node);

            fxmlLoader.load();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
