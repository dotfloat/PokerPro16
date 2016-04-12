package org.gruppe2.game.controller;

import org.gruppe2.game.event.PlayerActionQuery;
import org.gruppe2.game.event.PlayerJoinEvent;
import org.gruppe2.game.model.GameModel;
import org.gruppe2.game.model.PlayerModel;
import org.gruppe2.game.session.SessionContext;
import org.gruppe2.game.view.GameView;

import java.util.List;

public class GameController extends Controller<GameModel, GameView> {
    private final RoundController roundController = new RoundController(getSessionContext());

    public GameController(SessionContext sessionContext) {
        super(sessionContext);
    }

    @Override
    public void update() {
        roundController.update();
    }

    public GameModel getModel() {
        return (GameModel) getSessionContext().getModel(GameModel.class);
    }

    public GameView getView() {
        return (GameView) getSessionContext().getView(GameView.class);
    }

    public boolean addPlayer(PlayerModel model) {
        List<PlayerModel> players = getSessionContext().getModels(PlayerModel.class);

        if (!(players.size() + 1 >= getModel().getMaxPlayers())) {
            return false;
        }

        players.add(model);

        return true;
    }

    public RoundController getRoundController() {
        return roundController;
    }
}
