package org.gruppe2.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import org.gruppe2.game.Action;
import org.gruppe2.game.Card;
import org.gruppe2.game.Card.Suit;
import org.gruppe2.game.Player;
import org.gruppe2.game.RoundPlayer;
import org.gruppe2.game.event.ChatEvent;
import org.gruppe2.game.event.CommunityCardsEvent;
import org.gruppe2.game.event.Event;
import org.gruppe2.game.event.PlayerActionQuery;
import org.gruppe2.game.event.PlayerPostActionEvent;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.session.Helper;
import org.gruppe2.game.session.Query;


/**
 * Reader and writer for net protocol
 */
public class ProtocolConnection {
    private final SocketChannel channel;
    private final StringBuffer readBuffer = new StringBuffer();

    @Helper
    private GameHelper game;
    
    public ProtocolConnection(SocketChannel channel) {
        this.channel = channel;
    }

    public void sendMessage(String message) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(message.length() * 2);
        byteBuffer.clear();
        byteBuffer.put(message.getBytes());
        byteBuffer.flip();

        channel.write(byteBuffer);
    }

    public String[] readMessage() throws IOException {
        readAll();

        int indexOfCLRF;

        if ((indexOfCLRF = readBuffer.indexOf("\r\n")) < 0)
            return null;

        String message = readBuffer.substring(0, indexOfCLRF);
        readBuffer.delete(0, indexOfCLRF + 2);

        return splitMessage(message);
    }

    private void readAll() throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.flip();
        byteBuffer.clear();

        while (channel.read(byteBuffer) > 0) {
            readBuffer.append(new String(byteBuffer.array(), 0, byteBuffer.position()));
            byteBuffer.clear();
        }
    }

    private String[] splitMessage(String msg) {
        int indexOfColon = msg.indexOf(":");

        if (indexOfColon < 0) {
            return msg.split(";");
        }

        String[] args = msg.substring(0, indexOfColon).split(";");
        String rest = msg.substring(indexOfColon + 1);

        String[] output = Arrays.copyOf(args, args.length + 1);

        output[args.length] = rest;

        return output;
    }

    /**
     * Creates an event from the list of commands given by the protocol reader method
     *
     * @param listOfCommands
     * @return
     */
    public static Event parseEvent(String[] listOfCommands) {

        if (listOfCommands.length > 0) {
            switch (listOfCommands[0]) {
                case "CHAT":
                    UUID playerUUID = UUID.fromString(listOfCommands[1]);
                    String message = listOfCommands[2];
                    return new ChatEvent(message, playerUUID);
                case "ACTION":
                	return actionParser(listOfCommands);
                case "COMMUNITY":
                	return  communityCardsParser(listOfCommands);
                
            }
        }
        return null;
    }
    private static Event communityCardsParser(String[] listOfCommands) {
    	ArrayList<Card> cards = new ArrayList<Card>();
    	String cardsString = listOfCommands[1];
    	//TODO Get the real cards, not fake ones.
    	cards.add(new Card(2,Suit.CLUBS));
    	cards.add(new Card(3,Suit.CLUBS));
    	cards.add(new Card(4,Suit.CLUBS));
		return new CommunityCardsEvent(cards);
	}

	/**
     * Returns a PlayerPostActionEvent, and sets the players action, so that it happens ingame for each client
     * @param listOfCommands
     * @return
     */
	private static Event actionParser(String[] listOfCommands) {
		UUID playerUUID = UUID.fromString(listOfCommands[1]);
		Player player = null; //USE uuid to find player
		RoundPlayer roundPlayer = null; // Same here
		Action action = specificActionParser(listOfCommands[2], player, roundPlayer);
		
		return new PlayerPostActionEvent(player,roundPlayer,action);
	}
	/**
	 * This is a test for showing how action parses might be, but something is missing.
	 */
	private static Action specificActionParser(String actionString, Player player, RoundPlayer roundPlayer) {
		PlayerActionQuery playerActionQuery = new PlayerActionQuery(player,roundPlayer);
		Query<Action> actionQuery = null;
		
		switch (actionString) {
		case "CALL":
			Action call = new Action.Call();
			actionQuery.set(call);
			return call;
		}
		return null;
	}
    
    


}