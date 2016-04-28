package org.gruppe2.game.controller;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

import org.gruppe2.game.event.ChatEvent;
import org.gruppe2.game.session.Handler;
import org.gruppe2.game.session.Message;


public class NetworkServerController extends AbstractController {
    ServerSocketChannel serverSocket;
    ArrayList<SocketChannel> clients = new ArrayList<SocketChannel>();

    @Override
    public void update() {
        if (serverSocket != null) {
            try {
                SocketChannel client = serverSocket.accept();
                if (client != null) {
                    client.configureBlocking(false);
                    clients.add(client);

                    System.out.println("Someone connected");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < clients.size(); i++) {
            /* Listen to what the assholes say */
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
        sendToAll("CHAT " + event.getPlayerUUID() + " :" + event.getMessage() + "\r\n");
    }

    private void sendToAll(String mesg) {
        for (int i = 0; i < clients.size(); i++) {
            SocketChannel client = clients.get(i);

            ByteBuffer buf = ByteBuffer.allocate(mesg.length());
            buf.clear();
            buf.put(mesg.getBytes());
            buf.flip();

            try {
                client.write(buf);
            } catch (IOException e) {
                clients.remove(i--);
            }
        }
    }
}


