package org.gruppe2.backend;

public class Player {
    private String name;
    private int chips;
    private Card[] cards;
    private GameClient client;

    public Player(String name, int chips, GameClient client) {
        this.cards = new Card[2];
        this.name = name;
        this.chips = chips;
        this.client = client;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getChips() {
        return chips;
    }

    public void setChips(int chips) {
        this.chips = chips;
    }

    public Card[] getCards() {
        return cards;
    }

    public void setCards(Card card0, Card card1) {
        this.cards[0] = card0;
        this.cards[1] = card1;
    }

    public GameClient getClient() {
        return client;
    }

    public void setClient(GameClient client) {
        this.client = client;
    }
}
