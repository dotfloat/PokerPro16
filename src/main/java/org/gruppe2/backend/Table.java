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
     * Draws card on to the table depending on the round (0-3)
     * @param round Game round
     */
    public void drawCommunityCards(int round) {
        if (round == 1)
            communityCards = deck.drawCards(3);
        else if (round == 2)
            communityCards.add(deck.drawCard());
        else if (round == 3)
            communityCards.add(deck.drawCard());
        else if (round != 0)
            throw new IndexOutOfBoundsException();
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
    public void addToPot(int p) {
        pot = pot + p;
    }

    /**
     * @return pot on the table
     */
    public int getPot() {
        return pot;
    }

    public Deck getDeck() {
        return deck;
    }
}
