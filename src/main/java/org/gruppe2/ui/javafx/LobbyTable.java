package org.gruppe2.ui.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import org.gruppe2.ui.Resources;

/**
 * Created by Petter on 27/04/2016.
 */
public class LobbyTable extends StackPane {

    @FXML private ImageView tableImage;
    @FXML private StackPane table;
    @FXML private Label players;

    public LobbyTable(){
        Resources.loadFXML(this);
        setSize();
    }

    private void setSize() {
        double size = 0.13;
        tableImage.preserveRatioProperty().setValue(true);
        tableImage.fitWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(size));
        players.fontProperty().bind(ChoiceBar.fontTracking);
    }
}
