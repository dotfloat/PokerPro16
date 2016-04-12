package org.gruppe2.game.controller;

import org.gruppe2.game.event.PlayerActionQuery;
import org.gruppe2.game.old.Action;
import org.gruppe2.game.model.PlayerModel;
import org.gruppe2.game.view.PlayerView;
import org.gruppe2.game.session.SessionContext;

public class PlayerController extends Controller<PlayerModel, PlayerView> {
    private PlayerActionQuery query = null;

    public PlayerController(SessionContext sessionContext) {
        super(sessionContext);
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {

    }

    @Override
    public PlayerModel getModel() {
        return null;
    }

    @Override
    public PlayerView getView() {
        return null;
    }

    public Action pollAction() {
        if (query != null) {
            Action action = query.poll();

            if (action != null) {
                query = null;
                return action;
            }
        } else {
            query = new PlayerActionQuery();

            getSessionContext().getEventQueue().addEvent(PlayerActionQuery.class, query);
        }

        return null;
    }
}
