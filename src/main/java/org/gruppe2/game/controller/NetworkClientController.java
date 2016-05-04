package org.gruppe2.game.controller;

import java.io.IOException;
import java.util.UUID;

import org.gruppe2.game.Player;
import org.gruppe2.game.event.Event;
import org.gruppe2.game.event.PlayerJoinEvent;
import org.gruppe2.game.event.PlayerLeaveEvent;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.model.NetworkClientModel;
import org.gruppe2.game.session.Helper;
import org.gruppe2.game.session.Message;
import org.gruppe2.game.session.Model;
import org.gruppe2.network.ProtocolConnection;

public class NetworkClientController extends AbstractController {
    @Model
    private NetworkClientModel model;

    @Helper
    private GameHelper game;

    @Override
    public void update() {
        try {
            String[] message = model.getConnection().readMessage();

            if (message == null)
                return;

            messageSwitch(message);

        } catch (IOException e) {
            e.printStackTrace();
            getContext().quit();
        }
    }

    private void messageSwitch(String[] message) {
        UUID uuid;
        Player player;
        switch (message[0]) {
            case "PLAYER JOINED":
                uuid = UUID.fromString(message[1]);
                String name = message[3];
                String avatar = message[2];

                player = new Player(uuid, name, avatar, false);

                game.getModel().getPlayers().add(player);
                addEvent(new PlayerJoinEvent(player));

                break;

            case "PLAYER LEFT":
                uuid = UUID.fromString(message[1]);

                player = game.findPlayerByUUID(uuid);
                game.getPlayers().remove(player);

                addEvent(new PlayerLeaveEvent(player));
                break;
            default:
                Event event = ProtocolConnection.parseEvent(message);

                if (event != null)
                    addEvent(event);
                break;
        }
    }

    @Message
    public void addPlayer(UUID uuid, String name, String avatar) {
        try {
            model.getConnection().sendMessage("JOIN;" + uuid + ";" + avatar + ":" + name + "\r\n");
        } catch (IOException e) {
            e.printStackTrace();
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
