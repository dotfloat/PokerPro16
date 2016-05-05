package org.gruppe2.game.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.gruppe2.game.Action;
import org.gruppe2.game.Card;
import org.gruppe2.game.Cards;
import org.gruppe2.game.Player;
import org.gruppe2.game.RoundPlayer;
import org.gruppe2.game.event.*;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.helper.RoundHelper;
import org.gruppe2.game.model.NetworkClientModel;
import org.gruppe2.game.session.Handler;
import org.gruppe2.game.session.Helper;
import org.gruppe2.game.session.Message;
import org.gruppe2.game.session.Model;
import org.gruppe2.ui.javafx.ingame.Game;

public class NetworkClientController extends AbstractController {
    @Model
    private NetworkClientModel model;

    @Helper
    private GameHelper game;
    @Helper
    private RoundHelper round;

    //	player.getAction().isDone()
    @Override
    public void update() {
        try {
            String[] message = model.getConnection().readMessage();

            if (message == null)
                return;

            messageSwitch(message);

        } catch (IOException e) {
            e.printStackTrace();
            getContext().quit();
        }
    }

    private void messageSwitch(String[] message) {

        switch (message[0]) {

            default:
                Event event = parseEvent(message);

                if (event != null)
                    addEvent(event);
                break;
        }
    }

    @Message
    public void addPlayer(UUID uuid, String name, String avatar) {
        try {
            model.getConnection().sendMessage(
                    "JOIN;" + uuid + ";" + avatar + ":" + name + "\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Message
    public void chat(String message, UUID playerUUID) {
        sendMessage(String.format("SAY:%s\r\n", message));
    }

    @Message
    public void action(String message, UUID playerUUID) {
        sendMessage(String.format("SAY:%s\r\n", message));
    }

    @Handler
    public void onQuit(QuitEvent quitEvent) {
        sendMessage("DISSCONNECT");
    }


    private void sendMessage(String message) {
        try {
            model.getConnection().sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
            getContext().quit();
        }
    }

    /**
     * Creates an event from the list of commands given by the protocol reader
     * method
     *
     * @param listOfCommands
     * @return
     */
    public Event parseEvent(String[] listOfCommands) {
        UUID uuid;
        Player player;
        Optional<Player> optionalPlayer;
        Optional<RoundPlayer> optionalRoundPlayer;
        if (listOfCommands.length > 0) {
            switch (listOfCommands[0]) {
                case "CHAT":
                    UUID playerUUID = UUID.fromString(listOfCommands[1]);
                    String message = listOfCommands[2];
                    return new ChatEvent(message, playerUUID);

                case "PRE ACTION":
                    uuid = UUID.fromString(listOfCommands[1]);
                    optionalPlayer = game.findPlayerByUUID(uuid);

                    if (optionalPlayer.isPresent())
                        return new PlayerPreActionEvent(optionalPlayer.get());

                    break;

                case "POST ACTION":
                    return actionParser(listOfCommands);

                case "COMMUNITY":
                    return communityCardsParser(listOfCommands);

                case "PLAYERCARDS":
                	uuid = Game.getPlayerUUID();
                	optionalRoundPlayer = round.findPlayerByUUID(uuid);
                	if(optionalRoundPlayer.isPresent())
                		setPlayerCardsParser(optionalRoundPlayer,listOfCommands[1]);	
                    break;

                case "YOUR TURN":
                    UUID playerUUID1 = UUID.fromString(listOfCommands[1]);
                    optionalPlayer = game.findPlayerByUUID(playerUUID1);
                    optionalRoundPlayer = round.findPlayerByUUID(playerUUID1);

                    if (optionalPlayer.isPresent() || optionalRoundPlayer.isPresent())
                        return new PlayerActionQuery(optionalPlayer.get(), optionalRoundPlayer.get());

                    break;

                case "BLIND":
                    uuid = UUID.fromString(listOfCommands[1]);
                    optionalPlayer = game.findPlayerByUUID(uuid);
                    optionalRoundPlayer = round.findPlayerByUUID(uuid);

                    int value = Integer.valueOf(listOfCommands[2]);

                    if (optionalPlayer.isPresent() && optionalRoundPlayer.isPresent())
                        return new PlayerPaysBlind(optionalPlayer.get(), optionalRoundPlayer.get(), value);
                    break;

                case "CONNECTED":
                    uuid = UUID.fromString(listOfCommands[1]);
                    String name = listOfCommands[4];
                    String avatar = listOfCommands[2];
                    int tablePosition = Integer.parseInt(listOfCommands[3]);

                    player = new Player(uuid, name, avatar, false, tablePosition);

                    game.getModel().getPlayers().add(player);
                    return new PlayerJoinEvent(player);

                case "DISCONNECTED":
                    uuid = UUID.fromString(listOfCommands[1]);
                    optionalPlayer = game.findPlayerByUUID(uuid);
                    game.getPlayers().remove(optionalPlayer.get());
                    return new PlayerLeaveEvent(optionalPlayer.get());

                case "WON":
                    uuid = UUID.fromString(listOfCommands[1]);
                    optionalPlayer = game.findPlayerByUUID(uuid);
                    game.getPlayers().remove(optionalPlayer.get());
                    return new PlayerWonEvent(optionalPlayer.get());

                case "ROUND START":
                    List<RoundPlayer> active = round.getActivePlayers();
                    active.clear();

                    boolean done = false;
                    for (int i = game.getButton(); !done; i++) {
                        int j = (i + 1) % game.getPlayers().size();
                        Player p = game.getPlayers().get(j);
                        if (p.getBank() > 0)
                            active.add(new RoundPlayer(p.getUUID(), null, null));
                        done = j == game.getButton();
                    }

                    round.addPlayersToMap(active);

                    round.setPot(0);
                    round.setHighestBet(0);
                    round.setCurrent(0);
                    round.resetRound();
                    round.getCommunityCards().clear();
                	
                    return new RoundStartEvent();

                case "ROUND END":
                    return new RoundStartEvent();

            }


        }
        return null;
    }

    private void setPlayerCardsParser(
			Optional<RoundPlayer> optionalRoundPlayer, String cards) {
		List<Card> cardsList = Cards.asList(cards);
		Card[] primCardsList = new Card[2];
		int i = 0;
		for(Card card : cardsList){
			primCardsList[i] = card;
			i++;
		}
    	optionalRoundPlayer.get().setCards(primCardsList);
		
	}

	private Event communityCardsParser(String[] listOfCommands) {

        String cardsString = listOfCommands[1];
        List<Card> cards = Cards.asList(cardsString);
        return new CommunityCardsEvent(cards);
    }

    /**
     * Returns a PlayerPostActionEvent, and sets the players action, so that it
     * happens ingame for each client
     *
     * @param listOfCommands
     * @return
     */
    private Event actionParser(String[] listOfCommands) {
        UUID playerUUID = UUID.fromString(listOfCommands[1]);
        Optional<Player> player = game.findPlayerByUUID(playerUUID); // USE uuid to find player
        Optional<RoundPlayer> roundPlayer = round.findPlayerByUUID(playerUUID); // Same here

        if (!player.isPresent() || !roundPlayer.isPresent())
            return null;

        Action action = specificActionParser(listOfCommands, player.get(), roundPlayer.get());

        return new PlayerPostActionEvent(player.get(), roundPlayer.get(), action);
    }

    /**
     * This is a test for showing how action parses might be, but something is
     * missing.
     */
    private Action specificActionParser(String[] listOfCommands, Player player,
                                        RoundPlayer roundPlayer) {
        String actionString = listOfCommands[2];
        // PlayerActionQuery playerActionQuery = new
        // PlayerActionQuery(player,roundPlayer);

        switch (actionString) {
            case "Call":
                Action call = new Action.Call();
                return call;

            case "Check":
                Action check = new Action.Check();
                return check;

            case "Fold":
                Action fold = new Action.Fold();
                return fold;

            case "Raise":
                int raiseValue = Integer.valueOf(listOfCommands[3]);
                Action raise = new Action.Raise(raiseValue);
                return raise;

        }
        return null;
    }
}
