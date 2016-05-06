package org.gruppe2.game.controller;

import org.gruppe2.game.Action;
import org.gruppe2.game.event.*;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.helper.RoundHelper;
import org.gruppe2.game.model.GameModel;
import org.gruppe2.game.model.NetworkClientModel;
import org.gruppe2.game.model.RoundModel;
import org.gruppe2.game.session.*;

import java.io.IOException;
import java.util.UUID;

public class NetworkClientController extends AbstractController {

    @Model
    private NetworkClientModel model;

    @Helper
    private GameHelper game;
    @Helper
    private RoundHelper round;

    Query<Action> actionQuery;

    //	player.getAction().isDone()
    @Override
    public void update() {
        try {
            Object object;

            while ((object = model.getConnection().readObject()) != null) {
                if (object instanceof Event) {
                    addEvent((Event) object);
                } else if (object instanceof RoundModel) {
                    round.getModel().apply((RoundModel) object);
                } else if (object instanceof GameModel) {
                    game.getModel().apply((GameModel) object);
                }
            }

            checkForAction();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            getContext().quit();
        }
    }

    @Handler
    public void onPlayerJoin(PlayerJoinEvent e) {
        game.getPlayers().add(e.getPlayer());
    }

    @Handler
    public void onPlayerLeave(PlayerLeaveEvent e) {
        game.getPlayers().remove(e.getPlayer());
    }

    private void checkForAction() {

        if (actionQuery != null && actionQuery.isDone()) {
            Action action = actionQuery.get();

            System.out.println(action.toNetworkString());
            sendMessage(String.format("ACTION;%s\r\n", action.toNetworkString()));
            actionQuery = null;
        }
    }

    @Handler
    public void setActionQuery(PlayerActionQuery query) {
        actionQuery = query.getPlayer().getAction();
    }

    @Message
    public void addPlayer(UUID uuid, String name, String avatar) {
        try {
            model.getConnection().sendMessage(
                    "JOIN;" + uuid + ";" + avatar + ":" + name + "\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Message
    public void chat(String message, UUID playerUUID) {
        sendMessage(String.format("SAY:%s\r\n", message));
    }

    @Handler
    public void onQuit(QuitEvent quitEvent) {
        sendMessage("DISCONNECT\r\n");
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
