package org.gruppe2.ui.javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class PokerApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        StackPane root = new StackPane();
        root.getChildren().add(new CloseMe());

        stage.setTitle("PokerPro16");
        stage.setScene(new Scene(root, 300, 250));
        stage.show();

        stage.setMinWidth(300);
        stage.setMinHeight(250);
    }
}
