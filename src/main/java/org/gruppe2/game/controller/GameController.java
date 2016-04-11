package org.gruppe2.game.controller;

import org.gruppe2.game.model.GameModel;
import org.gruppe2.game.session.SessionContext;
import org.gruppe2.game.view.GameView;
import org.gruppe2.game.controller.AbstractPlayerController;

import java.util.*;

public class GameController extends AbstractController<GameModel, GameView> {
    private Map<UUID, AbstractPlayerController> players = new HashMap<>();

    public GameController(SessionContext sessionContext) {
        super(sessionContext);
    }

    public void update() {
    }

    public Map<UUID, AbstractPlayerController> getPlayers() {
        return players;
    }

    public void addPlayer(AbstractPlayerController controller) {
        players.put(controller.getModel().getUuid(), controller);
    }
}
