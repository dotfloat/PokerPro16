package org.gruppe2.game.controller;

import org.gruppe2.game.Handler;
import org.gruppe2.game.Query;
import org.gruppe2.game.event.*;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.helper.PlayerHelper;
import org.gruppe2.game.model.GameModel;
import org.gruppe2.game.model.RoundModel;
import org.gruppe2.game.model.RoundPlayerModel;
import org.gruppe2.game.Action;

import java.util.List;
import java.util.UUID;

public class RoundController extends AbstractController {
    private GameHelper gameHelper;
    private PlayerHelper playerHelper;
    private Query<Action> actionQuery = null;
    private RoundPlayerModel actionPlayer = null;

    @Override
    public void init() {
        super.init();

        gameHelper = new GameHelper(getContext());
        playerHelper = new PlayerHelper(getContext());
    }

    @Override
    public void update() {
        super.update();

        if (!getModel().isPlaying()) {
            if (gameHelper.canStart()) {
                start();
            }
        } else {
            if(getModel().getActivePlayers().size() == 1){
                getModel().setPlaying(false);
                addEvent(new PlayerWonEvent(playerHelper.getPlayerByUUID(getModel().getActivePlayers().get(0).getUUID())));
                return;
            }

            if (actionQuery == null) {
                nextPlayer();
                return;
            }

            if (actionQuery.isDone()) {
                addEvent(new PlayerPostActionEvent(playerHelper.getPlayerByUUID(actionPlayer.getUUID()), actionQuery.get()));

                if(actionQuery.get() instanceof Action.Fold){
                    getModel().getActivePlayers().remove(actionPlayer);
                    getModel().setCurrent(getModel().getCurrent()-1);
                }
                actionPlayer = null;
                actionQuery = null;
            }
        }
    }

    private void start() {
        getModel().setPlaying(true);
        getModel().getActivePlayers().clear();
        getModel().setCurrent(-1);
        getModel(GameModel.class).getPlayers().forEach((UUID uuid) -> getModel().getActivePlayers().add(new RoundPlayerModel(uuid)));

        addEvent(new RoundStartEvent());
    }

    private void nextPlayer() {
        int current = (getModel().getCurrent() + 1) % getModel().getActivePlayers().size();
        getModel().setCurrent(current);

        addEvent(new PlayerPreActionEvent(playerHelper.getPlayerByUUID(getCurrentPlayer().getUUID())));



        actionQuery = new Query<>();
        actionPlayer = getCurrentPlayer();

        addEvent(new PlayerActionQuery(playerHelper.getPlayerByUUID(getCurrentPlayer().getUUID()), actionQuery));
    }

    private RoundPlayerModel getCurrentPlayer() {
        return getModel().getActivePlayers().get(getModel().getCurrent());
    }

    private RoundModel getModel() {
        return getModel(RoundModel.class);
    }

    @Handler
    public void onPlayerLeave(PlayerLeaveEvent event) {
        int indexOf = -1 ;

        List<RoundPlayerModel> activePlayers = getModel().getActivePlayers();
        for (int i = 0; i < activePlayers.size(); i++) {
            RoundPlayerModel model = activePlayers.get(i);
            if (model.getUUID().equals(event.getPlayerModel().getUUID())) {
                indexOf = i;
            }
        }

        if (getModel().getCurrent() > indexOf) {
            getModel().setCurrent(getModel().getCurrent() - 1);
        }

        activePlayers.remove(indexOf);
    }

    @Handler
    public void botAction(PlayerActionQuery event) {
        if (!event.getPlayerModel().isBot())
            return;

        event.getQuery().set(new Action.Fold());
    }
}
