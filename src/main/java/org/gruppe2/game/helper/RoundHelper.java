package org.gruppe2.game.helper;

import org.gruppe2.game.model.RoundModel;
import org.gruppe2.game.model.RoundPlayerModel;
import org.gruppe2.game.session.SessionContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public class RoundHelper {
    private RoundModel model;

    public RoundHelper(SessionContext context) {
        model = context.getModel(RoundModel.class);
    }

    public boolean isPlaying() {
        return model.isPlaying();
    }

    public void setPlaying(boolean playing) {
        model.setPlaying(playing);
    }

    public RoundModel getModel() {
        return model;
    }

    public int getCurrent() {
        return model.getCurrent();
    }

    public List<RoundPlayerModel> getActivePlayers() {
        return model.getActivePlayers();
    }

    public void setCurrent(int current) {
        model.setCurrent(current);
    }

    public UUID getCurrentUUID() {
        return getActivePlayers().get(getCurrent()).getUUID();
    }

    public RoundPlayerModel getCurrentPlayer() {
        return getActivePlayers().get(getCurrent());
    }

    public void setPot(int pot) {
        model.setPot(pot);
    }

    public void setHighestBet(int highestBet) {
        model.setHighestBet(highestBet);
    }

    public Optional<RoundPlayerModel> findPlayerByUUID(UUID uuid) {
        return findPlayer(p -> p.getUUID().equals(uuid));
    }

    public Optional<RoundPlayerModel> findPlayer(Predicate<RoundPlayerModel> predicate) {
        return model.getActivePlayers().stream()
                .filter(predicate)
                .findFirst();
    }
}
