package org.gruppe2.ui.javafx.ingame;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.gruppe2.game.Player;
import org.gruppe2.game.event.ChatEvent;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.helper.RoundHelper;
import org.gruppe2.game.session.Handler;
import org.gruppe2.game.session.Helper;
import org.gruppe2.ui.UIResources;
import org.gruppe2.ui.javafx.PokerApplication;

import java.util.Random;
import java.util.UUID;

public class ChatBox extends VBox {

    @Helper
    private GameHelper gameHelper;
    @Helper
    private RoundHelper roundHelper;

    @FXML
    private GridPane chatArea;
    @FXML
    private TextField chatField;
    @FXML
    private ScrollPane scrollPane;

    private DoubleProperty nameWidth = new SimpleDoubleProperty();
    private DoubleProperty messageWidth = new SimpleDoubleProperty();

    private int numLines = 1;

    public ChatBox() {
        UIResources.loadFXML(this);
        Game.setAnnotated(this);

        nameWidth.bind(maxWidthProperty().multiply(0.2));
        messageWidth.bind(maxWidthProperty().subtract(nameWidth));
    }

    @FXML
    public void onChatAction(ActionEvent event) {
        Game.message("chat", chatField.getText(), Game.getPlayerUUID());
        chatField.setText("");
    }

    private boolean checkForCommands(String text) {
        String command = text.toLowerCase();

        switch (command) {
            case "/besthand":
                // throw new NotImplementedException();
                System.out.println("Not implemented yet");
                // String answer =
                // GeneralCalculations.getBestHandForPlayer(((GameScene)this.getParent().getParent()).communityCardsBox.getCommunityCards(),
                // player).toString();
                // this.setText(this.getText() + "\n" + player +
                // "s possible best hand is: " + answer);S
            case "/log":
                addLine(command + "is epic");
                // Print logs--->
                return true;
            case "/fuck off":
                addLine(command + "is epic");
                // Print playFuckOfClip--->
                return true;
            case "/raiding party":
                addLine(command + "is epic");
                // Print raidingPartyClip--->
                return true;
            default:
                return false;
        }
    }

    @Handler
    public void chatHandler(ChatEvent chatEvent) {
        if (chatEvent.getMessage().isEmpty())
            return;

        if (chatEvent.getMessage().charAt(0) == '/') {
            checkForCommands(chatEvent.getMessage());
            return;
        }

        Player player = gameHelper.findPlayerByUUID(chatEvent.getPlayerUUID()).get();

        addPlayerMessage(chatEvent.getPlayerUUID(), player.getName(), chatEvent.getMessage());
    }

    private void addPlayerMessage(UUID playerUUID, String name, String message) {
        Text playerName = new Text(name);
        Text messageNode = new Text(message);

        playerName.setFill(getPlayerColor(playerUUID));
        messageNode.setFill(Color.WHITE);

        playerName.fontProperty().bind(PokerApplication.getApplication().fontProperty());
        messageNode.fontProperty().bind(PokerApplication.getApplication().fontProperty());

        TextFlow messageFlow = new TextFlow(messageNode);
        messageFlow.maxWidthProperty().bind(messageWidth);

        chatArea.add(playerName, 1, numLines);
        chatArea.add(messageFlow, 2, numLines);

        numLines++;

        scrollPane.setVvalue(1.0);
    }

    private void addLine(String text) {
        Text textNode = new Text(text);
        TextFlow textFlow = new TextFlow(textNode);

        textNode.setFill(Color.WHITE);
        textNode.fontProperty().bind(PokerApplication.getApplication().fontProperty());

        chatArea.add(textFlow, 1, numLines, 2, 1);

        numLines++;

        scrollPane.setVvalue(1.0);
    }

    private Color getPlayerColor(UUID uuid) {
        Random random = new Random(uuid.getLeastSignificantBits() - uuid.getMostSignificantBits());

        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = (int) Math.sqrt(r * g);

        return Color.rgb(r, g, b);
    }
}
