package org.gruppe2.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
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
import org.gruppe2.game.event.PlayerActionQuery;
import org.gruppe2.game.event.PlayerPostActionEvent;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.helper.RoundHelper;
import org.gruppe2.game.session.Helper;
import org.gruppe2.game.session.Query;
import org.gruppe2.ui.javafx.ingame.Game;


/**
 * Reader and writer for net protocol
 */
public class ProtocolConnection {
    private final SocketChannel channel;
    private final StringBuffer readBuffer = new StringBuffer();

    @Helper
    private GameHelper gameHelper;
    @Helper
    private RoundHelper roundHelper;
    
    
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

   
}