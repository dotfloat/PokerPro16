package org.gruppe2.game.model;

import org.gruppe2.game.Action;
import org.gruppe2.game.old.Card;
import org.gruppe2.game.old.PossibleActions;
import org.gruppe2.game.session.Query;

import java.util.UUID;

public class PlayerModel {
    private final UUID uuid;
    private final String name;
    private final String avatar;
    private final Query<Action> action;
    private final boolean bot;

    private volatile int bank;
    private volatile int bet;
    private PossibleActions options;
    private Card card1;
    private Card card2;

    public PlayerModel(UUID uuid, String name, String avatar, Query<Action> action, boolean bot) {
        this.uuid = uuid;
        this.name = name;
        this.avatar = avatar;
        this.action = action;
        this.bot = bot;
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }

    public boolean isBot() {
        return bot;
    }

    public int getBank() {
        return bank;
    }

    public void setBank(int bank) {
        this.bank = bank;
    }

    public Query<Action> getAction() {
        return action;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public PossibleActions getOptions() {
        return options;
    }

    public void setOptions(PossibleActions options) {
        this.options = options;
    }

    public void setCards(Card card1, Card card2){
        this.card1 = card1;
        this.card2 = card2;
    }

    public Card getCard1() {
        return card1;
    }

    public Card getCard2() {
        return card2;
    }
}
