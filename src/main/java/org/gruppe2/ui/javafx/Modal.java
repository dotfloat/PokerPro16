package org.gruppe2.ui.javafx;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import org.gruppe2.ui.UIResources;

/**
 * Created by Petter on 26/04/2016.
 */

public class Modal {
    private Pane parent;
    private BorderPane borderPane;

    private Label title;
    private Button closeButton;

    public Modal() {
        parent = new Pane();
        borderPane = new BorderPane();

        HBox titleBar = new HBox(5);
        titleBar.setAlignment(Pos.CENTER);

        title = new Label("Modal Window");
        title.fontProperty().bind(PokerApplication.getApplication().smallFontProperty());

        closeButton = new Button("X");
        closeButton.setOnAction(this::onCloseButtonAction);
        closeButton.fontProperty().bind(PokerApplication.getApplication().smallFontProperty());

        titleBar.getChildren().add(title);
        titleBar.getChildren().add(closeButton);

        borderPane.setTop(titleBar);
        borderPane.setStyle("-fx-background-color: black");
        borderPane.layoutXProperty().bind(parent.widthProperty().divide(2).subtract(borderPane.widthProperty().divide(2)));
        borderPane.layoutYProperty().bind(parent.heightProperty().divide(2).subtract(borderPane.heightProperty().divide(2)));

        parent.setOnKeyPressed(this::onKeyPressed);
        parent.setStyle("-fx-background-color: radial-gradient(center 50% 50%, radius 150%, rgba(0,0,0,0) 15%,rgba(0,0,0,1) 50%)");
        parent.getChildren().add(borderPane);
    }

    public void setPercentSize(double x, double y) {
        borderPane.prefWidthProperty().bind(parent.widthProperty().multiply(x));
        borderPane.prefHeightProperty().bind(parent.heightProperty().multiply(y));
    }

    public void setTitle(String text) {
        title.setText(text);
    }

    public void setContent(Node node) {
        borderPane.setCenter(node);
    }

    public void show() {
        PokerApplication.getRoot().getChildren().add(parent);
    }

    public void close() {
        PokerApplication.getRoot().getChildren().remove(parent);
    }

    private void onCloseButtonAction(ActionEvent actionEvent) {
        close();
    }

    public void onKeyPressed(KeyEvent event) {
        System.out.println(event);
        if (event.getCode() == KeyCode.ESCAPE)
            close();
    }

    public static void messageBox(String title, String message) {
        Modal modal = new Modal();
        modal.setPercentSize(0.3, 0.2);
        modal.setTitle(title);
        modal.setContent(new Label(message));
        modal.show();
    }
}
