package org.gruppe2.game.helper;

import org.gruppe2.game.Player;
import org.gruppe2.game.model.GameModel;
import org.gruppe2.game.session.SessionContext;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class GameHelper {
    private final GameModel model;

    public GameHelper(SessionContext context) {
        model = context.getModel(GameModel.class);
    }

    public int getButton() {
        return model.getButton();
    }

    public Player getButtonPlayer() {
        return model.getPlayers().get(model.getButton());
    }

    public boolean canStart() {
        return model.getPlayers().size() > model.getMinPlayers();
    }

    public List<Player> getPlayers() {
        return model.getPlayers();
    }

    public Player findPlayerByUUID(UUID uuid) {
        return findPlayer(p -> p.getUUID().equals(uuid));
    }

    public Player findPlayerByName(String name) {
        return findPlayer(p -> p.getName().equals(name));
    }

    public Player findPlayer(Predicate<Player> predicate) {
        return model.getPlayers().stream()
                .filter(predicate)
                .findFirst()
                .get();
    }

    public GameModel getModel() {
        return model;
    }

    public int getBuyIn() {
        return model.getBuyIn();
    }
}
