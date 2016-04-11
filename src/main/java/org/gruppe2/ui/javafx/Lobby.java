package org.gruppe2.ui.javafx;

import com.sun.xml.internal.bind.v2.TODO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import org.gruppe2.ui.Resources;


/**
 * Created by Petter on 11/04/2016.
 */
public class Lobby extends BorderPane {

    @FXML private TextField search;
    @FXML private Button submit;
    @FXML private CheckBox checkBoxFriends;
    @FXML private TilePane lobbyTiles;
    @FXML private BorderPane lobby;

    public Lobby(){
        Resources.loadFXML(this);
        setSize();
        search.prefWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.3));
        submit.prefWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.1));
    }

    public void search(){
        System.out.print("Searching");
        lobbyTiles.getChildren().add(new Label(search.getText()));
    }

    public void friendBox(){
        lobbyTiles.getChildren().add(new Label("Displaying table with friends"));
    }
    public void closeLobby(){
        SceneController.removeModal(this);
    }
    private void setSize(){
        lobby.maxWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.7));
        lobby.maxHeightProperty().bind(PokerApplication.getRoot().heightProperty().multiply(0.7));
    }

    private void updateTables(String search){
        if (checkBoxFriends.selectedProperty().get()){
            //check for tables with friends on
        }
        //TODO query for all tables and add them as child to lobbyTiles
    }
}
