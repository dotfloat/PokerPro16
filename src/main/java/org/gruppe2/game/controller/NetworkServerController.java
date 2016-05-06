package org.gruppe2.game.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

import org.gruppe2.game.Action;
import org.gruppe2.game.Player;
import org.gruppe2.game.RoundPlayer;
import org.gruppe2.game.event.*;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.helper.RoundHelper;
import org.gruppe2.game.model.GameModel;
import org.gruppe2.game.model.RoundModel;
import org.gruppe2.game.session.Handler;
import org.gruppe2.game.session.Helper;
import org.gruppe2.game.session.Message;
import org.gruppe2.game.session.Query;
import org.gruppe2.network.ConnectedClient;
import org.gruppe2.network.NetworkIO;


public class NetworkServerController extends AbstractController {
    private ServerSocketChannel serverSocket;
    private ArrayList<ConnectedClient> clients = new ArrayList<>();

    @Helper
    private GameHelper gameHelper;
    @Helper
    private RoundHelper roundHelper;
    
    private Query<Action> action = null;

    @Override
    public void init() {
        getContext().getEventQueue().setGenericHandler(e -> {
            broadcastObject(gameHelper.getModel());
            broadcastObject(roundHelper.getModel());
            broadcastObject(e);
        });
    }

    @Override
    public void update() {
        if (serverSocket != null) {
            try {
                SocketChannel client = serverSocket.accept();
                if (client != null) {
                    NetworkIO connection = new NetworkIO(client);
                    client.configureBlocking(false);
                    addClient(connection);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < clients.size(); i++) {
            try {
                String[] args = clients.get(i).getConnection().readMessage();

                if (args == null)
                    continue;

                UUID uuid;
                switch (args[0]) {
                    case "SAY":
                        uuid = clients.get(i).getPlayerUUID();
                        if (uuid == null)
                            continue;

                        broadcastObject(new ChatEvent(args[1], uuid));
                        break;
                    case "JOIN":
                        clients.get(i).setPlayerUUID(UUID.fromString(args[1]));
                        getContext().message("addPlayer", clients.get(i).getPlayerUUID(), args[3], args[2]);
                        break;
                    case "DISCONNECT":
                        uuid = clients.get(i).getPlayerUUID();
                        Optional<Player> player = gameHelper.findPlayerByUUID(uuid);

                        if (player.isPresent()) {
                        	getContext().message("kickPlayer", player.get().getUUID());
                            Optional<RoundPlayer> roundPlayer = roundHelper.findPlayerByUUID(uuid);

                            if (roundPlayer.isPresent())
                                roundHelper.getActivePlayers().remove(roundPlayer.get());

                            addEvent(new PlayerLeaveEvent(player.get()));
                        }

                        clients.remove(i--);
                        break;
                    case "ACTION":
                    	System.out.println("server recieved action");
                    	uuid = clients.get(i).getPlayerUUID();
                    	
                    	//setPlayerActionFromMessage(uuid,args);
                }
            } catch (IOException e) {
                clients.remove(i--);
            }
        }
    }

	@Message
    public void listen() {
        try {
            serverSocket = ServerSocketChannel.open();
            serverSocket.socket().bind(new InetSocketAddress(8888));
            serverSocket.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Message
    public void addClient(NetworkIO connection) {
        clients.add(new ConnectedClient(connection));

        try {
            connection.sendObject(gameHelper.getModel());
            connection.sendObject(roundHelper.getModel());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void broadcastObject(Object object)  {
        for (int i = 0; i < clients.size(); i++) {
            try {
                clients.get(i).getConnection().sendObject(object);
            } catch (IOException e) {
                e.printStackTrace();
                clients.remove(i--);
            }
        }
    }
}


