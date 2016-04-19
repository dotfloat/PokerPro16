package org.gruppe2.game.helper;

import org.gruppe2.game.model.GameModel;
import org.gruppe2.game.model.PlayerModel;
import org.gruppe2.game.session.SessionContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public class GameHelper {
    private final GameModel model;

    public GameHelper(SessionContext context) {
        model = context.getModel(GameModel.class);
    }

    public PlayerModel getButtonPlayer() {
        return model.getPlayers().get(model.getButton());
    }

    public boolean canStart() {
        return model.getPlayers().size() > model.getMinPlayers();
    }

    public List<PlayerModel> getPlayers() {
        return model.getPlayers();
    }

    public Optional<PlayerModel> findPlayerByUUID(UUID uuid) {
        return findPlayer(p -> p.getUUID().equals(uuid));
    }

    public Optional<PlayerModel> findPlayerByName(String name) {
        return findPlayer(p -> p.getName().equals(name));
    }

    public Optional<PlayerModel> findPlayer(Predicate<PlayerModel> predicate) {
        return model.getPlayers().stream()
                .filter(predicate)
                .findFirst();
    }

    public GameModel getModel() {
        return model;
    }
}
