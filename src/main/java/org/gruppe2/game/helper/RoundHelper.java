package org.gruppe2.game.helper;

import org.gruppe2.game.Player;
import org.gruppe2.game.RoundPlayer;
import org.gruppe2.game.model.RoundModel;
import org.gruppe2.game.session.SessionContext;

import java.util.List;
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

    public List<RoundPlayer> getActivePlayers() {
        return model.getActivePlayers();
    }

    public void setCurrent(int current) {
        model.setCurrent(current);
    }

    public UUID getCurrentUUID() {
        return getActivePlayers().get(getCurrent()).getUUID();
    }

    public RoundPlayer getCurrentPlayer() {
        return getActivePlayers().get(getCurrent());
    }

    public void setPot(int pot) {
        model.setPot(pot);
    }

    public void setHighestBet(int highestBet) {
        model.setHighestBet(highestBet);
    }

    public RoundPlayer findPlayerByUUID(UUID uuid) {
        return findPlayer(p -> p.getUUID().equals(uuid));
    }

    public RoundPlayer findPlayer(Predicate<RoundPlayer> predicate) {
        return model.getActivePlayers().stream()
                .filter(predicate)
                .findFirst()
                .get();
    }

    public int getHighestBet() {
        return model.getHighestBet();
    }

    public void addToPot(int addToTablePot) {
        model.setPot(model.getPot() + addToTablePot);
    }
}
