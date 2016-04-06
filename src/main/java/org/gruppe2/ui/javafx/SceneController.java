package org.gruppe2.ui.javafx;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import java.net.URL;

/**
 * Created by kjors on 06.04.2016.
 *
 * Changes scene in PokerApplication main root BorderPane
 * #url = URL to fxml-file
 */
public class SceneController {


    public static void setScene(URL url) {

        try {
        	
            BorderPane newScene = FXMLLoader.load( url );
            BorderPane stage = PokerApplication.getRoot();
            stage.setCenter( newScene );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
