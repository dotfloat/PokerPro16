package org.gruppe2.game;

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

    public void addToBank(int chips) {
        bank += chips;
    }

    public int getTablePosition() {
        return tablePosition;
    }

    public void setTablePosition(int tablePosition) {
        this.tablePosition = tablePosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (card0 != null ? !card0.equals(player.card0) : player.card0 != null) return false;
        return !(card1 != null ? !card1.equals(player.card1) : player.card1 != null);

    }

    @Override
    public int hashCode() {
        int result = card0 != null ? card0.hashCode() : 0;
        result = 31 * result + (card1 != null ? card1.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return name;
    }
}
