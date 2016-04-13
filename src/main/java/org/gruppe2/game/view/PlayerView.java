package org.gruppe2.game.view;

import org.gruppe2.game.event.EventHandler;
import org.gruppe2.game.event.PlayerActionEvent;
import org.gruppe2.game.event.PlayerActionQuery;
import org.gruppe2.game.model.PlayerModel;
import org.gruppe2.game.session.SessionContext;

public class PlayerView extends AbstractView<PlayerModel> {

    public void onAction(EventHandler<PlayerActionEvent> handler) {
    }

    public void onActionQuery(EventHandler<PlayerActionQuery> handler) {
    }
}
