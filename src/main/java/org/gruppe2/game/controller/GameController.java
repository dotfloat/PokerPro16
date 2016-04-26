package org.gruppe2.game.controller;

import org.gruppe2.ai.NewDumbAI;
import org.gruppe2.game.Action;
import org.gruppe2.game.event.PlayerJoinEvent;
import org.gruppe2.game.model.GameModel;
import org.gruppe2.game.session.Helper;
import org.gruppe2.game.session.Message;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.Player;
import org.gruppe2.game.session.Query;

import java.util.UUID;

public class GameController extends AbstractController {
    @Helper
    private GameHelper game;

    @Override
    public void update() {
        if (game.getModel().getBotPolicy() == GameModel.BotPolicy.FILL) {
            if (game.getPlayers().stream().allMatch(Player::isBot))
                return;

            if (game.getPlayers().size() < game.getModel().getMaxPlayers()) {
                addBot();
            }
        }
    }

    @Message
    public boolean addBot() {
        return addPlayerModel(NewDumbAI.generateModel());
    }

    @Message
    public boolean addPlayer(UUID uuid, String name, String avatar) {
        return addPlayerModel(new Player(uuid, name, avatar, false));
    }

    @Message
    public void kickPlayer(UUID uuid) {
    }

    private boolean addPlayerModel(Player model) {
        game.getModel().getPlayers().add(model);

        if (game.getPlayers().size() >= game.getModel().getMaxPlayers()) {
            return false;
        }

        if (game.canStart()) {
            getContext().message("roundStart");
        }

        model.setBank(game.getBuyIn());

        addEvent(new PlayerJoinEvent(model));

        return true;
    }
}
