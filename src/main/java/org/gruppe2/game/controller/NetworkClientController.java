package org.gruppe2.game.controller;

import org.gruppe2.game.event.ChatEvent;
import org.gruppe2.game.session.Message;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.UUID;

public class NetworkClientController extends AbstractController {
    private SocketChannel socket = null;

    @Override
    public void update() {
        if (socket != null && socket.isConnected()) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.flip();
            buffer.clear();
            int bytesRead = 0;

            try {
                if ((bytesRead = socket.read(buffer)) == 0)
                    return;
            } catch (IOException e) {
                e.printStackTrace();
                socket = null;
                getContext().quit();
            }

            String string = new String(buffer.array());

            System.out.println(string);
            addEvent(new ChatEvent(string, UUID.randomUUID()));
        }
    }

    @Message
    public void connect() {
        try {
            socket = SocketChannel.open();
            socket.connect(new InetSocketAddress(8888));
            socket.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Connection failed");
            getContext().quit();
        }

        System.out.println("Connected");
    }
}
