package org.gruppe2.game.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.gruppe2.game.Action;
import org.gruppe2.game.Card;
import org.gruppe2.game.Cards;
import org.gruppe2.game.Player;
import org.gruppe2.game.RoundPlayer;
import org.gruppe2.game.event.ChatEvent;
import org.gruppe2.game.event.CommunityCardsEvent;
import org.gruppe2.game.event.Event;
import org.gruppe2.game.event.PlayerJoinEvent;
import org.gruppe2.game.event.PlayerLeaveEvent;
import org.gruppe2.game.event.PlayerPaysBlind;
import org.gruppe2.game.event.PlayerPostActionEvent;
import org.gruppe2.game.event.PlayerPreActionEvent;
import org.gruppe2.game.event.PlayerWonEvent;
import org.gruppe2.game.event.QuitEvent;
import org.gruppe2.game.event.RoundStartEvent;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.helper.RoundHelper;
import org.gruppe2.game.model.NetworkClientModel;
import org.gruppe2.game.session.Handler;
import org.gruppe2.game.session.Helper;
import org.gruppe2.game.session.Message;
import org.gruppe2.game.session.Model;

public class NetworkClientController extends AbstractController {
	@Model
	private NetworkClientModel model;

	@Helper
	private GameHelper game;
	@Helper
	private RoundHelper roundHelper;
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
		if (listOfCommands.length > 0) {
			switch (listOfCommands[0]) {
			case "CHAT":
				UUID playerUUID = UUID.fromString(listOfCommands[1]);
				String message = listOfCommands[2];
				return new ChatEvent(message, playerUUID);
			case "ACTION":
				return actionParser(listOfCommands);
			case "COMMUNITY":
				return communityCardsParser(listOfCommands);
			case "PLAYERCARDS":
//				uuid = game.findPlayerByUUID(playerUUID);
			case "YOUR TURN":
				UUID playerUUID1 = UUID.fromString(listOfCommands[1]);
				player = game.findPlayerByUUID(playerUUID1);
				return new PlayerPreActionEvent(player);
			case "BLIND":
				UUID playerUUID2 = UUID.fromString(listOfCommands[1]);
				Player player1 = game.findPlayerByUUID(playerUUID2);
				RoundPlayer roundPlayer1 = roundHelper.findPlayerByUUID(playerUUID2);
				int value = Integer.valueOf(listOfCommands[2]);
				return new PlayerPaysBlind(player1,roundPlayer1,value);
			case "CONNECTED":
				uuid = UUID.fromString(listOfCommands[1]);
				String name = listOfCommands[3];
				String avatar = listOfCommands[2];
				player = new Player(uuid, name, avatar, false);
				game.getModel().getPlayers().add(player);
				return new PlayerJoinEvent(player);
			case "DISCONNECTED":
				uuid = UUID.fromString(listOfCommands[1]);
				player = game.findPlayerByUUID(uuid);
				game.getPlayers().remove(player);
				return new PlayerLeaveEvent(player);
			case "WON":
				uuid = UUID.fromString(listOfCommands[1]);
				player = game.findPlayerByUUID(uuid);
				game.getPlayers().remove(player);
				return new PlayerWonEvent(player);
			case "ROUND START":
				return new RoundStartEvent();
			case "ROUND END":
				return new RoundStartEvent();
				
			}
			
			
			
		}
		return null;
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
		Player player = game.findPlayerByUUID(playerUUID); // USE uuid to find
															// player
		RoundPlayer roundPlayer = roundHelper.findPlayerByUUID(playerUUID); // Same
																			// here
		Action action = specificActionParser(listOfCommands, player,
				roundPlayer);

		return new PlayerPostActionEvent(player, roundPlayer, action);
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
