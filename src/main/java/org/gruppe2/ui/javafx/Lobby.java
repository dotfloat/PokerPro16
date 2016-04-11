package org.gruppe2.ui.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;


/**
 * Created by Petter on 11/04/2016.
 */
public class Lobby extends BorderPane {

    @FXML private TextField search;
    @FXML private Button submit;
    @FXML private CheckBox checkBoxFriends;
    @FXML private TilePane lobbyTile;

    public Lobby(){
        search.prefWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.3));
        submit.prefWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.1));
    }

    public void search(){
        System.out.print("Searching");
        lobbyTile.getChildren().add(new Label("Searching"));
    }

    public void friendBox(){
        System.out.print("Displaying only friends");
        lobbyTile.getChildren().add(new Label("Displaying table with friends"));
    }
}
