package org.gruppe2.ui.javafx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.Region;

import org.gruppe2.game.event.ChatEvent;
import org.gruppe2.game.session.Handler;
import org.gruppe2.ui.Resources;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ChatBox extends TextArea {
    
	public ChatBox() {
        Resources.loadFXML(this);
        InGame.getContext().setAnnotated(this);
        setPositionsAndSettings();
    }

    private void setPositionsAndSettings() {
        double chatScale = 0.22;
        this.prefWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(chatScale));
        this.prefHeightProperty().bind(PokerApplication.getRoot().heightProperty().multiply(chatScale));
        this.maxWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(chatScale));
        this.maxHeightProperty().bind(PokerApplication.getRoot().heightProperty().multiply(chatScale));

        editableProperty().setValue(false);
        setWrapText(true);
    }

    public void setEventListeners(TextField textField) {
        textField.setOnAction(e -> { // Put text from textField to textArea
            if (textField.getText().equals(null) || textField.getText().equals("")) setScrollTop(Double.MAX_VALUE);

            else if (checkForCommands(textField)) ;

            else {
            	System.out.println("heo");
            	InGame.getContext().message("chat", textField.getText());
            }
            textField.setText("");
        });
        makeTransparent();
    }

    private void makeTransparent() {
        this.skinProperty().addListener(new ChangeListener<Skin<?>>() {

            @Override
            public void changed(
                    ObservableValue<? extends Skin<?>> ov, Skin<?> t, Skin<?> t1) {
                if (t1 != null && t1.getNode() instanceof Region) {
                    Region r = (Region) t1.getNode();
                    r.setBackground(Background.EMPTY);

                    r.getChildrenUnmodifiable().stream().
                            filter(n -> n instanceof Region).
                            map(n -> (Region) n).
                            forEach(n -> n.setBackground(Background.EMPTY));

                    r.getChildrenUnmodifiable().stream().
                            filter(n -> n instanceof Control).
                            map(n -> (Control) n).
                            forEach(c -> c.skinProperty().addListener(this)); // *
                }
            }
        });

    }
    private boolean checkForCommands(TextField textField) {
        String command = textField.getText().toLowerCase();

        switch (command) {
            case "besthand":
                throw new NotImplementedException();
                //String answer = GeneralCalculations.getBestHandForPlayer(((InGame)this.getParent().getParent()).communityCardsBox.getCommunityCards(), player).toString();
                //this.setText(this.getText() + "\n" + player + "s possible best hand is: " + answer);S
            case "log":
                this.setText(this.getText() + "\n: " + textField.getText() + "is epic");
                //Print logs--->
                return true;
            case "fuck off":
                this.setText(this.getText() + "\n: " + textField.getText() + "is epic");
                //Print playFuckOfClip--->
                return true;
            case "raiding party":
                this.setText(this.getText() + "\n: " + textField.getText() + "is epic");
                //Print raidingPartyClip--->
                return true;
            default:
                return false;
        }
    }
    @Handler
    public void chatHandler(ChatEvent chatEvent){
        this.setText(this.getText() + "\n: " + chatEvent.getMessage());
    }
}
