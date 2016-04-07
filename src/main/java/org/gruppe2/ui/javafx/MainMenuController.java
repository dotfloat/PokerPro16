package org.gruppe2.ui.javafx;


import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 */
public class MainMenuController implements Initializable {
    @FXML private ImageView logo;
    @FXML private Button createTable;
    @FXML private Button joinTable;
    @FXML private Button singlePlayer;
    @FXML private Button viewStatistics;
    @FXML private Button settings;
    @FXML private VBox vBox;

    public void goToSinglePlayer() throws InterruptedException {
        SceneController.setScene(getClass().getResource("/views/GameWindow.fxml"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logo.fitWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.8));
        vBox.maxWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.3));

        setButtonSize();
        fadeIn();
    }
    private void fadeIn(){
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(800), vBox);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }
    private void setButtonSize(){
        createTable.setMaxWidth(Double.MAX_VALUE);
        joinTable.setMaxWidth(Double.MAX_VALUE);
        singlePlayer.setMaxWidth(Double.MAX_VALUE);
        viewStatistics.setMaxWidth(Double.MAX_VALUE);
        settings.setMaxWidth(Double.MAX_VALUE);
    }
}
