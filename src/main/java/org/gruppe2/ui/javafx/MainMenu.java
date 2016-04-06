package org.gruppe2.ui.javafx;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 */
public class MainMenu implements Initializable {
    @FXML private ImageView logo;
    @FXML private Button createTable;
    @FXML private Button joinTable;
    @FXML private Button singlePlayer;
    @FXML private Button viewStatistics;
    @FXML private Button settings;
    @FXML private VBox vBox;
    private double height = PokerApplication.getHeight();
    private double width = PokerApplication.getWidth();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logo.fitWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.8));
        vBox.maxWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.3));

        createTable.setMaxWidth(Double.MAX_VALUE);
        joinTable.setMaxWidth(Double.MAX_VALUE);
        singlePlayer.setMaxWidth(Double.MAX_VALUE);
        viewStatistics.setMaxWidth(Double.MAX_VALUE);
        settings.setMaxWidth(Double.MAX_VALUE);

    }
}
