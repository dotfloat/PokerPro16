package org.gruppe2.game.helper;

import org.gruppe2.game.Card;
import org.gruppe2.game.Player;
import org.gruppe2.game.PossibleActions;
import org.gruppe2.game.RoundPlayer;
import org.gruppe2.game.model.GameModel;
import org.gruppe2.game.model.RoundModel;
import org.gruppe2.game.session.SessionContext;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class RoundHelper {
    private RoundModel model;
    private GameHelper helper;

    public RoundHelper(SessionContext context) {
        model = context.getModel(RoundModel.class);
        helper = new GameHelper(context);
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

    public UUID getLastActivePlayerID() {
        return model.getActivePlayers().get(model.getNumberOfActivePlayers()-1).getUUID();
    }

    public void nextRound() {
        model.setRoundNumber(model.getRoundNumber()+1);
    }

    public void resetRound() {
        model.setRoundNumber(0);
    }

    public int getRoundNum() {
        return model.getRoundNumber();
    }

    public PossibleActions getPlayerOptions (UUID id) {
        PossibleActions options = new PossibleActions();
        Player player = helper.findPlayerByUUID(id);
        RoundPlayer roundPlayer = findPlayerByUUID(id);

        if (roundPlayer.getBet() == getHighestBet())
            options.setCheck();

        if (player.getBank() >= getHighestBet() - roundPlayer.getBet())
            if (getHighestBet() - roundPlayer.getBet() != 0)
                options.setCall(getHighestBet() - roundPlayer.getBet());

        if (!player.getUUID().equals(getLastRaiserID())) {
            int maxRaise = player.getBank() + roundPlayer.getBet() - getHighestBet();
            if (maxRaise > 0)
                options.setRaise(1, maxRaise);
        }

        if (!options.canCall() && !options.canCheck() && !options.canRaise())
            options.setAllIn();

        return options;
    }

    public void setLastRaiserID(UUID lastRaiserID) {
        model.setLastRaiserID(lastRaiserID);
    }

    public UUID getLastRaiserID() {
        return model.getLastRaiserID();
    }

    public List<Card> getCommunityCards() {
        return model.getCommunityCards();
    }
}
