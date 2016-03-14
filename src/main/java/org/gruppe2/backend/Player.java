package org.gruppe2.backend;

public class Player {
    private String name;
    private int bank;
    private int bet;
    private Card[] cards;
    private GameClient client;

    public Player(String name, int chips, GameClient client) {
        this.cards = new Card[2];
        this.name = name;
        this.bank = bank;
        this.client = client;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Card[] getCards() {
        return cards;
    }

    public void setCards(Card card0, Card card1) {
        this.cards[0] = card0;
        this.cards[1] = card1;
    }

    public int getBank() {
        return bank;
    }

    public void setBank(int bank) {
        this.bank = bank;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public GameClient getClient() {
        return client;
    }
}
