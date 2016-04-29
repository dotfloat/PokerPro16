package org.gruppe2.game.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.UUID;

import org.gruppe2.game.event.ChatEvent;
import org.gruppe2.game.event.PlayerPostActionEvent;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.helper.RoundHelper;
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
                    System.out.println("Someone connected");
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

    /**
     * To send objects over tcp socket channel
     *
     * @param object
     */
    private void sendToAll(Object object) {
        for (int i = 0; i < clients.size(); i++) {
            byte[] byteObject = null;

            try {
                byteObject = serialize(object);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            SocketChannel client = clients.get(i).getChannel();
            ByteBuffer buf = ByteBuffer.allocate(byteObject.length);
            buf.clear();
            buf.put(byteObject);
            buf.flip();
            try {
                client.write(buf);
            } catch (IOException e) {
                clients.remove(i--);
                onPlayerDisconnect();
            }
        }
    }

    private void sendToAll(String mesg) {
        for (int i = 0; i < clients.size(); i++) {
            SocketChannel client = clients.get(i).getChannel();

            ByteBuffer buf = ByteBuffer.allocate(mesg.length());
            buf.clear();
            buf.put(mesg.getBytes());
            buf.flip();

            try {
                client.write(buf);
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


