package org.gruppe2.game.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.gruppe2.ai.NewDumbAI;
import org.gruppe2.game.Player;
import org.gruppe2.game.event.PlayerJoinEvent;
import org.gruppe2.game.event.PlayerLeaveEvent;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.model.GameModel;
import org.gruppe2.game.session.Helper;
import org.gruppe2.game.session.Message;

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
        List<Player> players;

        synchronized (players = game.getPlayers()) {
            Optional<Player> player = game.findPlayerByUUID(uuid);

            if (!player.isPresent())
                return;

            game.getPlayers().remove(player.get());

            addEvent(new PlayerLeaveEvent(player.get()));
        }
    }

    private boolean addPlayerModel(Player model) {
        List<Player> players;

        synchronized (players = game.getPlayers()) {
            if (players.size() >= game.getModel().getMaxPlayers()) {
                Optional<Player> findBot = players.stream()
                        .filter(Player::isBot)
                        .findFirst();

                if (findBot.isPresent()) {
                    kickPlayer(findBot.get().getUUID());
                } else {
                    return false;
                }
            }

            players.add(model);

            if (game.canStart()) {
                getContext().message("roundStart");
            }

            model.setBank(game.getBuyIn());

            addEvent(new PlayerJoinEvent(model));

            return true;
        }
    }
}
