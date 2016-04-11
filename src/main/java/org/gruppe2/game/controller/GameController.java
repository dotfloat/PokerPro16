package org.gruppe2.game.controller;

import org.gruppe2.game.event.EventHandler;
import org.gruppe2.game.event.PlayerActionQuery;
import org.gruppe2.game.model.GameModel;
import org.gruppe2.game.model.PlayerModel;
import org.gruppe2.game.session.SessionContext;
import org.gruppe2.game.view.GameView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class GameController extends Controller<GameModel, GameView> {
    private List<PlayerController> playerControllers = new ArrayList<>();
    private Method getModelMethod = findContextGetMethod("Model");
    private Method getViewMethod = findContextGetMethod("View");

    public GameController(SessionContext sessionContext) {
        super(sessionContext);
    }

    @Override
    public void init() {
    }

    @Override
    public void update() {
        for (PlayerController controller : playerControllers) {
            controller.update();
        }
    }

    public void addPlayerController(PlayerController playerController) {
        playerControllers.add(playerController);
        playerController.init();
    }

    @Override
    public GameModel getModel() {
        try {
            return (GameModel) getModelMethod.invoke(getSessionContext());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public GameView getView() {
        try {
            return (GameView) getViewMethod.invoke(getSessionContext());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void addPlayer(PlayerModel model, EventHandler<PlayerActionQuery> handler) {
        getModel().getPlayers().add(model);
        getSessionContext().getEventQueue().registerHandler(PlayerActionQuery.class, model, handler);
    }
}
