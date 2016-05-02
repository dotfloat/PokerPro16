package org.gruppe2.game.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Base64;
import java.util.UUID;

import org.gruppe2.game.event.ChatEvent;
import org.gruppe2.game.event.CommunityCardsEvent;
import org.gruppe2.game.event.PlayerJoinEvent;
import org.gruppe2.game.event.PlayerPostActionEvent;
import org.gruppe2.game.event.RoundStartEvent;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.helper.RoundHelper;
import org.gruppe2.game.model.GameModel;
import org.gruppe2.game.model.RoundModel;
import org.gruppe2.game.session.Handler;
import org.gruppe2.game.session.Helper;
import org.gruppe2.game.session.Message;
import org.gruppe2.network.ConnectedClient;
import org.gruppe2.network.ProtocolConnection;


public class NetworkServerController extends AbstractController {
    private ServerSocketChannel serverSocket;
    private ArrayList<ConnectedClient> clients = new ArrayList<>();

    @Helper
    private GameHelper gameHelper;
    @Helper
    private RoundHelper roundHelper;

    @Override
    public void update() {
        if (serverSocket != null) {
            try {
                SocketChannel client = serverSocket.accept();
                if (client != null) {
                    ProtocolConnection connection = new ProtocolConnection(client);
                    client.configureBlocking(false);
                    clients.add(new ConnectedClient(connection));

                    syncModel(connection, GameModel.class);
                    syncModel(connection, RoundModel.class);
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

                switch (args[0]) {
                    case "SAY":
                    	UUID uuid = clients.get(i).getPlayerUUID();
                    	if (uuid == null)
                    		continue;
                    	
                        addEvent(new ChatEvent(args[1], uuid));
                        break;
                    case "JOIN":
                    	System.out.println("join event"+args[1]);
                    	
                    	clients.get(i).setPlayerUUID(UUID.fromString(args[1]));
                    	
                    	getContext().message("addPlayer", clients.get(i).getPlayerUUID(), args[3], args[2]);
                    	break;
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
    public void addClient(ProtocolConnection connection) {
    	clients.add(new ConnectedClient(connection));
    }
    
    @Handler
    public void onPlayerJoin(PlayerJoinEvent playerJoinEvent){
    	sendToAll("PLAYER JOINED;" + playerJoinEvent.getPlayer().getUUID() + ";" + playerJoinEvent.getPlayer().getAvatar() + ":"+playerJoinEvent.getPlayer().getName()+"\r\n");
    }
    @Handler
    public void onChat(ChatEvent event) {
        sendToAll("CHAT;" + event.getPlayerUUID() + ":" + event.getMessage() + "\r\n");
    }

    @Handler
    public void onPlayerAction(PlayerPostActionEvent actionEvent) {
        sendToAll("ACTION;" + actionEvent.getPlayer().getUUID() + ":" + actionEvent.getAction() + "\r\n");
    }
    @Handler
    public void onCommunityCards(CommunityCardsEvent communityCardsEvent){
    	communityCardsEvent.getCards();
    	sendToAll("COMMUNITYCARDS;" + "c02;c03;c04" + "\r\n");
    }
    @Handler
    public void onPlayerCards(RoundStartEvent roundStartEvent){
    	sendToAll("PLAYERCARDS;" + "c02;c03" + "\r\n");
    }


    public void onPlayerConnect() {
        sendToAll("CONNECTED;" + "PLAYER UUID" + "\r\n");
    }

    public void onPlayerDisconnect() {
        sendToAll("DISCONNECTED;" + "PLAYER UUID" + "\r\n");
    }

    private void syncModel(ProtocolConnection connection, Class<?> modelClass) throws IOException {
        byte[] byteObject = null;

        try {
            byteObject = serialize(getModel(modelClass));
        } catch (IOException e1) {
            e1.printStackTrace();
            return;
        }

        connection.sendMessage(String.format("SYNC;%s:%s\r\n", modelClass.getSimpleName(), new String(Base64.getEncoder().encode(byteObject))));
    }
    

    private void sendToAll(String mesg) {
        for (int i = 0; i < clients.size(); i++) {
            try {
                clients.get(i).getConnection().sendMessage(mesg);
            } catch (IOException e) {
                clients.remove(i--);
            }
        }
    }
    
    

    /**
     * Used to send objects over socket channel
     *
     * @param obj
     * @return
     * @throws IOException
     */
    public static byte[] serialize(Object obj) throws IOException {
        try (ByteArrayOutputStream b = new ByteArrayOutputStream()) {
            try (ObjectOutputStream o = new ObjectOutputStream(b)) {
                o.writeObject(obj);
            }
            return b.toByteArray();
        }
    }
}


