package org.gruppe2.game.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

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

                        addEvent(new ChatEvent(args[1], uuid));
                        break;
                    case "JOIN":
                        clients.get(i).setPlayerUUID(UUID.fromString(args[1]));
                        getContext().message("addPlayer", clients.get(i).getPlayerUUID(), args[3], args[2]);
                        break;
                    case "DISCONNECT":
                        uuid = clients.get(i).getPlayerUUID();
                        Optional<Player> player = gameHelper.findPlayerByUUID(uuid);

                        if (player.isPresent()) {
                            Optional<RoundPlayer> roundPlayer = roundHelper.findPlayerByUUID(uuid);

                            if (roundPlayer.isPresent())
                                roundHelper.getActivePlayers().remove(roundPlayer.get());

                            addEvent(new PlayerLeaveEvent(player.get()));
                        }

                        clients.remove(i--);
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

        try {
            syncModel(connection, GameModel.class);
            syncModel(connection, RoundModel.class);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Handler
    public void onPlayerJoin(PlayerJoinEvent playerJoinEvent) {
        sendToAll("CONNECTED;" + playerJoinEvent.getPlayer().getUUID() + ";" + playerJoinEvent.getPlayer().getAvatar() + ":" + playerJoinEvent.getPlayer().getName() + "\r\n");
    }

    @Handler
    public void onPlayerLeave(PlayerLeaveEvent playerLeaveEvent) {
        sendToAll(String.format("DISCONNECTED;%s\r\n", playerLeaveEvent.getPlayer().getUUID()));
    }

    @Handler
    public void onChat(ChatEvent event) {
        sendToAll("CHAT;" + event.getPlayerUUID() + ":" + event.getMessage() + "\r\n");
    }

    @Handler
    public void onPlayerPreAction(PlayerPreActionEvent actionEvent) {
        sendToAll("PRE ACTION;" + actionEvent.getPlayer().getUUID() + "\r\n");
    }

    @Handler
    public void onPlayerActionQuery(PlayerActionQuery query) {
        clients.stream()
                .filter(c -> query.equals(c.getPlayerUUID()))
                .findFirst()
                .ifPresent(c -> {
                    try {
                        c.getConnection().sendMessage("YOUR TURN\r\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    @Handler
    public void onPlayerPostAction(PlayerPostActionEvent actionEvent) {
        sendToAll("POST ACTION;" + actionEvent.getPlayer().getUUID() + ":" + actionEvent.getAction() + "\r\n");
    }

    @Handler
    public void onCommunityCards(CommunityCardsEvent communityCardsEvent) {
        sendToAll("COMMUNITYCARDS;" + communityCardsEvent.getCards() + "\r\n");
    }

    @Handler
    public void onRoundStart(RoundStartEvent roundStartEvent) {
        sendToAll("ROUND START;" + "\r\n");
        for (int i = 0; i < clients.size(); i++) {
            UUID uuid = clients.get(i).getPlayerUUID();
            Optional<RoundPlayer> roundPlayer = roundHelper.findPlayerByUUID(uuid);

            if (roundPlayer.isPresent()) {
                String playerCards = roundPlayer.get().getCards().toString();

                playerCards = "PLAYERCARDS;" + playerCards;

                sendToOne(i, playerCards);
            }
        }
    }

    private void sendToOne(int i, String message) {
        try {
            clients.get(i).getConnection().sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Handler
    public void onRoundEnd(RoundEndEvent roundEndEvent) {
        sendToAll("ROUND END;" + "\r\n");
    }

    @Handler
    public void onPlayerPaysBlind(PlayerPaysBlind playerPaysBlind) {
        sendToAll("BLIND;" + playerPaysBlind.getPlayer().getUUID() + ";" + playerPaysBlind.getBlindAmount() + "\r\n");
    }

    @Handler
    public void onPlayerWon(PlayerWonEvent playerWonEvent) {
        sendToAll("WON;" + playerWonEvent.getPlayer().getUUID() + "\r\n");
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


