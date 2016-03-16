package org.gruppe2.backend;

public class Player {
    private String name;
    private int bank;
    private int bet;
    private int tablePosition;
    private Card card0;
    private Card card1;
    private GameClient client;

    public Player(String name, int bank, GameClient client) {
        this.card0 = null;
        this.card1 = null;
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

    public Card getCard1() {
        return card0;
    }

    public Card getCard2() {
        return card1;
    }

    public void setCards(Card card0, Card card1) {
        this.card0 = card0;
        this.card1 = card1;
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

    public int getTablePosition() {
        return tablePosition;
    }

    public void setTablePosition(int tablePosition) {
        this.tablePosition = tablePosition;
    }
}
