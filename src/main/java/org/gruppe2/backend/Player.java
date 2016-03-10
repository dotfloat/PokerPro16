package org.gruppe2.backend;

/**
 * Created by Mikal on 09.03.16.
 */
public class Player {
    private String name;
    private int chips;
    private Card card1;
    private Card card2;

    public Player(String name, int startChips) {
        this.name = name;
        this.chips = startChips;
    }

    public void giveCards(Card c1, Card c2){
        card1 = c1;
        card2 = c2;
    }

    public void addChips(int chips){
        this.chips += chips;
    }

    public int getChips() {
        return chips;
    }

    public String getName(){
        return name;
    }

    public Card getCard1() {
        return card1;
    }

    public Card getCard2() {
        return card2;
    }
}
