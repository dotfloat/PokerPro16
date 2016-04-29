package org.gruppe2.game.controller;

import org.gruppe2.game.event.Event;
import org.gruppe2.game.model.NetworkClientModel;
import org.gruppe2.game.session.Message;
import org.gruppe2.game.session.Model;
import org.gruppe2.network.ProtocolConnection;

import java.io.IOException;
import java.util.UUID;

public class NetworkClientController extends AbstractController {
    @Model
    private NetworkClientModel model;

    @Override
    public void update() {
        try {
            String[] message = model.getConnection().readMessage();

            if (message == null)
                return;

            Event event = ProtocolConnection.parseEvent(message);

            if (event != null)
                addEvent(event);
        } catch (IOException e) {
            e.printStackTrace();
            getContext().quit();
        }
    }

    @Message
    public void chat(String message, UUID playerUUID) {
        sendMessage(String.format("SAY:%s\r\n", message));
    }

    private void sendMessage(String message) {
        try {
            model.getConnection().sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
            getContext().quit();
        }
    }
}
