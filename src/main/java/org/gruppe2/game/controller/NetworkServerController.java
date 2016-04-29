package org.gruppe2.game.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Base64;
import java.util.UUID;

import org.gruppe2.game.event.ChatEvent;
import org.gruppe2.game.event.PlayerPostActionEvent;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.helper.RoundHelper;
import org.gruppe2.game.model.GameModel;
import org.gruppe2.game.model.RoundModel;
import org.gruppe2.game.session.Handler;
import org.gruppe2.game.session.Helper;
import org.gruppe2.game.session.Message;
import org.gruppe2.network.ConnectedClient;
import org.gruppe2.network.ProtocolReader;


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
                    client.configureBlocking(false);
                    clients.add(new ConnectedClient(client));
                    onPlayerConnect();

                    syncModel(client, GameModel.class);
                    syncModel(client, RoundModel.class);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < clients.size(); i++) {
            SocketChannel channel = clients.get(i).getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.flip();
            buffer.clear();

            try {
                if (channel.read(buffer) <= 0)
                    continue;
            } catch (IOException e) {
                clients.remove(i--);
            }

            String[] args = ProtocolReader.reader(new String(buffer.array()));

            switch (args[0]) {
                case "SAY":
                    sendToAll("CHAT;" + UUID.randomUUID() + ":" + args[1]);
                    addEvent(new ChatEvent(args[1], UUID.randomUUID()));
                    break;
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

    @Handler
    public void onChat(ChatEvent event) {
        sendToAll("CHAT;" + event.getPlayerUUID() + ":" + event.getMessage() + "\r\n");
    }

    @Handler
    public void onPlayerAction(PlayerPostActionEvent actionEvent) {
        sendToAll("ACTION;" + actionEvent.getPlayer().getUUID() + ":" + actionEvent.getAction() + "\r\n");
    }


    public void onPlayerConnect() {
        sendToAll("CONNECTED;" + "PLAYER UUID" + "\r\n");
    }

    public void onPlayerDisconnect() {
        sendToAll("DISCONNECTED;" + "PLAYER UUID" + "\r\n");
    }

    private void syncModel(SocketChannel channel, Class<?> modelClass) throws IOException {
        byte[] byteObject = null;

        try {
            byteObject = serialize(getModel(modelClass));
        } catch (IOException e1) {
            e1.printStackTrace();
            return;
        }

        sendString(channel, String.format("SYNC;%s:%s\r\n", modelClass.getSimpleName(), new String(Base64.getEncoder().encode(byteObject))));
    }

    private void sendString(SocketChannel channel, String mesg) throws IOException {
        System.out.printf("[[%s]]", mesg);

        ByteBuffer buf = ByteBuffer.allocate(mesg.length() * 2);
        buf.clear();
        buf.put(mesg.getBytes());
        buf.flip();

        channel.write(buf);
    }

    private void sendToAll(String mesg) {
        for (int i = 0; i < clients.size(); i++) {
            try {
                sendString(clients.get(i).getChannel(), mesg);
            } catch (IOException e) {
                clients.remove(i--);
                onPlayerDisconnect();
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


