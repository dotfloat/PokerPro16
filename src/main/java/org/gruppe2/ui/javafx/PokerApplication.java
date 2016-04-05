package org.gruppe2.ui.javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class PokerApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        StackPane root = new StackPane();
        Intro intro = new Intro();

        root.getChildren().add(intro);

        stage.setTitle("PokerPro16");
        stage.setScene(new Scene(root, 1920*0.8, 1080*0.8));

        intro.fitWidthProperty().bind(stage.widthProperty());

        stage.show();

    }
}
