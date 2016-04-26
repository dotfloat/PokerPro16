package org.gruppe2.game;

import java.util.UUID;

public class RoundPlayer {
    private final UUID uuid;
    private final Card[] cards = new Card[2];

    private volatile int bet = 0;

    public RoundPlayer(UUID uuid, Card card1, Card card2) {
        this.uuid = uuid;
        this.cards[0] = card1;
        this.cards[1] = card2;
    }

    public UUID getUUID() {
        return uuid;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public Card[] getCards() {
        return cards;
    }
}