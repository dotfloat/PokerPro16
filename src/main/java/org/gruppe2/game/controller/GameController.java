package org.gruppe2.game.controller;

import org.gruppe2.ai.NewDumbAI;
import org.gruppe2.game.Message;
import org.gruppe2.game.event.PlayerJoinEvent;
import org.gruppe2.game.event.PlayerLeaveEvent;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.helper.PlayerHelper;
import org.gruppe2.game.model.GameModel;
import org.gruppe2.game.model.PlayerModel;

import java.util.List;
import java.util.UUID;

public class GameController extends AbstractController {
    private GameHelper gameHelper;
    private PlayerHelper playerHelper;

    @Override
    public void init() {
        super.init();

        gameHelper = new GameHelper(getContext());
        playerHelper = new PlayerHelper(getContext());
    }

    @Override
    public void update() {
        super.update();

        if(checkForPlayers())
        for (int i = getModel().getPlayers().size(); i < getModel().getMaxPlayers(); i++) {
            addBot();
        }
    }

    private boolean checkForPlayers(){
        for(int i = 0; i < getModel().getPlayers().size(); i++){
            if(!playerHelper.getPlayerByUUID(getModel().getPlayers().get(i)).isBot())
                return true;
        }
        return false;
    }

    @Message
    public void addBot() {
        addPlayerModel(NewDumbAI.generateModel());
    }

    @Message
    public void addPlayer(UUID uuid, String name, String avatar) {
        if (getModel().getMaxPlayers() == getModel().getPlayers().size()) {
            for (UUID botUUID : getModel().getPlayers()) {
                if (playerHelper.getPlayerByUUID(botUUID).isBot()) {
                    kickPlayer(botUUID);
                    break;
                }
            }
        }

        addPlayerModel(new PlayerModel(uuid, name, avatar, false));
    }

    @Message
    public void kickPlayer(UUID uuid) {
        PlayerModel removed = null;

        for (PlayerModel model : getModels(PlayerModel.class)) {
            if (model.getUUID().equals(uuid)) {
                getModels(PlayerModel.class).remove(model);
                removed = model;
                break;
            }
        }

        for (UUID playerUUID : getModel().getPlayers()) {
            if (playerUUID.equals(uuid)) {
                getModel().getPlayers().remove(uuid);
                break;
            }
        }

        if (removed != null) {
            addEvent(new PlayerLeaveEvent(removed));
        }
    }

    private GameModel getModel() {
        return getModel(GameModel.class);
    }

    private void addPlayerModel(PlayerModel model) {
        GameModel gameModel = getModel(GameModel.class);
        List<PlayerModel> playerModels = getModels(PlayerModel.class);

        if (gameModel.getPlayers().size() >= gameModel.getMaxPlayers())
            return;

        gameModel.getPlayers().add(model.getUUID());
        playerModels.add(model);
        addEvent(new PlayerJoinEvent(model));
    }
}
