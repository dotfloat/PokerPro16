package org.uib112g2;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JavaFXApp extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("JavaFXApp");

        Button btnNullPointer = new Button();
        btnNullPointer.setText("I <3 NullPointerException");
        btnNullPointer.setOnAction(ev -> ((Object) null).toString());

        Button btnHello = new Button();
        btnHello.setText("Hello");
        btnHello.setOnAction(ev -> new Alert(Alert.AlertType.INFORMATION, "Is it me you're looking for?").showAndWait());

        VBox vbox = new VBox(8);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(btnNullPointer, btnHello);

        StackPane root = new StackPane();
        root.getChildren().add(vbox);

        stage.setScene(new Scene(root, 300, 250));
        stage.show();
    }
}