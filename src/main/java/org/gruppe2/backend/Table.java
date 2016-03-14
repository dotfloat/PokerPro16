package org.gruppe2.backend;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for a Poker Table
 * Created by cemsepetcioglu on 10.03.16.
 */
public class Table {
    private Deck deck = new Deck();
    private List<Card> communityCards;
    private int pot;

    /**
     * Draws 5 community cards to the table
     */
    public void drawCommunityCards() {
        communityCards = deck.drawCards(5);
    }

    /**
     * @return an array of all community cards
     */
    public List<Card> getCommunityCards() {
        return communityCards;
    }

    /**
     * Adding pot to table
     * @param p pot value
     */
    public void setPot(int p) {
        pot = pot + p;
    }

    /**
     * @return pot on the table
     */
    public int setPot() {
        return pot;
    }

    public Deck getDeck() {
        return deck;
    }
}
