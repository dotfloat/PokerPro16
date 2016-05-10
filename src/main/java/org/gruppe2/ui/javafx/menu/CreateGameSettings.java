package org.gruppe2.ui.javafx.menu;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.gruppe2.ui.UIResources;

public class CreateGameSettings extends VBox {
    @FXML
    TextField tableName;
    @FXML
    TextField smallBlind;
    @FXML
    TextField bigBlind;
    @FXML
    TextField startMoney;
    @FXML
    TextField maxPlayers;

    public CreateGameSettings(){
        UIResources.loadFXML(this);
    }
}
