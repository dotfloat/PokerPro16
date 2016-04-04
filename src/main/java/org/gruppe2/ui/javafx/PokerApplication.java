package org.gruppe2.ui.javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class PokerApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        StackPane root = new StackPane();
        root.getChildren().add(new GameWindow());

        stage.setTitle("PokerPro16");
        stage.setScene(new Scene(root));
        stage.show();

    }
}
