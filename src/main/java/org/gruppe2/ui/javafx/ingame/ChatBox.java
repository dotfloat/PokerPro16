package org.gruppe2.ui.javafx.ingame;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.VPos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.gruppe2.game.Player;
import org.gruppe2.game.event.ChatEvent;
import org.gruppe2.game.event.PlayerWonEvent;
import org.gruppe2.game.event.RoundStartEvent;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.helper.RoundHelper;
import org.gruppe2.game.session.Handler;
import org.gruppe2.game.session.Helper;
import org.gruppe2.ui.UIResources;
import org.gruppe2.ui.javafx.PokerApplication;

import java.util.*;

public class ChatBox extends VBox {
    private static final double openedHeight = 400.0;
    private static final double closedHeight = 100.0;

    private final List<String> emotes = Arrays.asList(UIResources.listEmotes());

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
    private DoubleProperty chatHeight = new SimpleDoubleProperty(closedHeight);

    private int numLines = 1;

    public ChatBox() {
        UIResources.loadFXML(this);
        Game.setAnnotated(this);

        nameWidth.bind(widthProperty().multiply(0.2));
        messageWidth.bind(widthProperty().subtract(nameWidth));

        chatField.focusedProperty().addListener((o, oldVal, hasFocus) -> {
            if (hasFocus) {
                chatHeight.set(openedHeight);
                scrollPane.setStyle("-fx-background-color: #0d0d0d");
                scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            } else {
                chatHeight.set(closedHeight);
                scrollPane.setStyle("-fx-background-color: transparent");
                scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            }
        });
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

        addPlayerMessage(player, chatEvent.getMessage());
    }

    @Handler
    public void onRoundStart(RoundStartEvent event) {
        addLine("A new round has started");
    }

    @Handler
    public void onPlayerWon(PlayerWonEvent event) {
        addLine("Player " + event.getPlayer().getName() + " has won the round!");
    }

    private void addPlayerMessage(Player player, String message) {
        Text playerName = new Text(player.getName());

        playerName.setFill(getPlayerColor(player));
        playerName.fontProperty().bind(PokerApplication.getApplication().smallFontProperty());

        TextFlow messageFlow = parseEmotes(message);
        messageFlow.maxWidthProperty().bind(messageWidth);

        chatArea.add(playerName, 0, numLines);
        chatArea.add(messageFlow, 1, numLines);

        GridPane.setValignment(playerName, VPos.TOP);

        numLines++;

        scrollPane.setVvalue(1.0);
    }

    private void addLine(String text) {
        Text textNode = new Text(text);
        TextFlow textFlow = new TextFlow(textNode);

        textNode.setFill(Color.GRAY);
        textNode.fontProperty().bind(PokerApplication.getApplication().smallFontProperty());
        textFlow.maxWidthProperty().bind(prefWidthProperty());

        chatArea.add(textFlow, 0, numLines, 2, 1);

        numLines++;

        scrollPane.setVvalue(1.0);
    }

    private TextFlow parseEmotes(String message) {
        TextFlow flow = new TextFlow();
        String[] words = message.split("\\s+");
        String buffer = "";

        for (String word : words) {
            int indexOf;

            if ((indexOf = emotes.indexOf(word)) >= 0) {
                Text text = new Text(buffer);
                text.setFill(Color.GRAY);
                text.fontProperty().bind(PokerApplication.getApplication().smallFontProperty());
                flow.getChildren().add(text);
                buffer = " ";

                ImageView imageView = new ImageView(UIResources.getEmote(word));
                imageView.setPreserveRatio(true);
                imageView.fitHeightProperty().bind(PokerApplication.getApplication().scaleProperty().multiply(14));
                flow.getChildren().add(imageView);

                continue;
            }

            buffer += word + " ";
        }

        if (!buffer.isEmpty()) {
            Text text = new Text(buffer);
            text.setFill(Color.LIGHTGRAY);
            text.fontProperty().bind(PokerApplication.getApplication().smallFontProperty());
            flow.getChildren().add(text);
        }

        return flow;
    }

    private Color getPlayerColor(Player player) {
        return UIResources.getAvatarColor(player.getAvatar());
    }

    public double getChatHeight() {
        return chatHeight.get();
    }

    public DoubleProperty chatHeightProperty() {
        return chatHeight;
    }
}
