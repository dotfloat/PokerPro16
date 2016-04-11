package org.gruppe2.game.view;

import org.gruppe2.game.event.EventHandler;
import org.gruppe2.game.event.PlayerActionEvent;
import org.gruppe2.game.model.PlayerModel;
import org.gruppe2.game.controller.AbstractPlayerController;

public class PlayerView extends AbstractView<PlayerModel, AbstractPlayerController> {
    public PlayerView(AbstractPlayerController controller) {
        super(controller);
    }

    public void onAction(EventHandler<PlayerActionEvent> handler) {
        getController().getSessionContext().getEventQueue().registerHandler(PlayerActionEvent.class, handler);
    }
}
