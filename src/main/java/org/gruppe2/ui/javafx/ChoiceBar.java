package org.gruppe2.ui.javafx;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import org.gruppe2.game.old.Action;
import org.gruppe2.game.old.Player;
import org.gruppe2.game.old.PossibleActions;
import org.gruppe2.ui.Resources;

public class ChoiceBar extends HBox {
    private int width = PokerApplication.getWidth();
    private int height = PokerApplication.getHeight();
    private GUIPlayer client;
    private Player player;
    static ObjectProperty<Font> fontTracking = new SimpleObjectProperty<Font>(Font.getDefault());

    @FXML
    private TextField chatField;
    @FXML
    private Button FOLD;
    @FXML
    private Slider slider;
    @FXML
    private Label sliderValue;
    @FXML
    private Button BET;

    public ChoiceBar() {
        Resources.loadFXML(this);
        setSizes();
    }

    @FXML
    private void setSizes() {
        setFontListener();

        this.spacingProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.02));

        slider.prefWidthProperty().bind(
                PokerApplication.getRoot().widthProperty().multiply(0.2));
        slider.setMinWidth(width * 0.05);
        chatField.minWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.21));
        chatField.prefWidthProperty().bind(
                PokerApplication.getRoot().widthProperty().multiply(0.22));

        sliderValue.prefWidthProperty().bind(
                PokerApplication.getRoot().widthProperty().multiply(0.09));
        chatField.prefWidthProperty().bind(
                PokerApplication.getRoot().widthProperty().multiply(0.10));
        FOLD.prefWidthProperty().bind(
                PokerApplication.getRoot().widthProperty().multiply(0.09));
        BET.prefWidthProperty().bind(
                PokerApplication.getRoot().widthProperty().multiply(0.09));


    }

    private void setFontListener() {
        BET.fontProperty().bind(fontTracking);
        FOLD.fontProperty().bind(fontTracking);
        chatField.fontProperty().bind(fontTracking);
        this.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldWidth, Number newWidth) {
                fontTracking.set(Font.font(newWidth.doubleValue() / 70));
            }
        });
    }

    @FXML
    public void setEvents(GUIPlayer client) {
        this.client = client;
        this.player = client.getSession().getPlayers().get(0);
        FOLD.setOnAction(e -> foldAction());
        BET.setOnAction(e -> betAction());

        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                                Number oldValue, Number newValue) {
                sliderValue.textProperty().setValue(checkMaxBid(slider));
            }
        });
        setKeyListener();
    }

    /**
     * Makes it possible to use keys to play, instead of mouse
     */

    @SuppressWarnings("incomplete-switch")

    private void setKeyListener() {
        chatField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    slider.setValue(slider.getValue() * 2);
                    break;
                case DOWN:
                    slider.setValue(slider.getValue() / 2);
                    break;
                case LEFT:
                    foldAction();
                    break;
                case RIGHT:
                    betAction();
                    break;
            }
        });
    }

    /**
     * This will become fxml
     */
    private void foldAction() {
        client.setAction(new Action.Fold());
    }

    /**
     * This will become fxml
     */
    private void betAction() {
        PossibleActions pa = client.getSession().getPlayerOptions(player);
        
        if (pa.canRaise() && slider.getValue() > 1)
            raise(client, slider, player);
        else if (pa.canCheck())
            client.setAction(new Action.Check());
        else if (pa.canCall())
            client.setAction(new Action.Call());
        else if(pa.canAllIn()){
        	client.setAction(new Action.AllIn());
        }
    }

    private void raise(GUIPlayer client, Slider raiseSlider, Player player) {
        if (client.getSession().getPlayerOptions(player).getMinRaise() <= raiseSlider
                .getValue()) {
            if (client.getSession().getPlayerOptions(player).getMaxRaise() >= raiseSlider
                    .getValue())
                client.setAction(new Action.Raise((int) raiseSlider.getValue()));
        }
    }

    /**
     * If you raise all you have, change text of raise button to ALL IN
     *
     * @param slider
     * @return
     */
    private String checkMaxBid(Slider slider) {
        if (slider.getValue() == slider.getMax())
            BET.setText("ALL IN");
        else
            BET.setText("RAISE");
        return (int) slider.getValue() + " CHIPS";
    }

    /**
     * Removes eventpossibilities
     *
     * @param player
     */
    public void updatePossibleBarsToClick(Player player) {
        PossibleActions pa = client.getSession().getPlayerOptions(player);
        if (pa.canCall())
            BET.setText("CALL");

        if (pa.canRaise() && slider.getValue() > 1) {
            BET.getStyleClass().add("button");
            BET.setText("RAISE");
        } else if (pa.canCheck())
            BET.setText("CHECK");

        slider.setMax(pa.getMaxRaise());
        slider.setMin(pa.getMinRaise());
        slider.setValue(pa.getMinRaise());
    }
}
