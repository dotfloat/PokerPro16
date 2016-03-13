package org.gruppe2.ai;

import org.gruppe2.backend.Card;
import org.gruppe2.backend.Player;
import org.gruppe2.backend.PokerTable;
import org.gruppe2.backend.ShowdownEvaluator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HandEvaluator implements AIEvaluate {
    @Override
    public double evaluate(PokerTable table, Player bot) {
        List<Card> cards = getCardsFromTableAndHand(table, bot);
        ShowdownEvaluator se = new ShowdownEvaluator();
        ShowdownEvaluator.Hand hand = se.evaluate(cards);
        switch(hand) {
            case ROYALFLUSH:
                return 100;
            case STRAIGHTFLUSH:
                return 95;
            case FOUROFAKIND:
                return 90;
            case FULLHOUSE:
                return 85;
            case FLUSH:
                return 80;
            case STRAIGHT:
                return 75;
            case THREEOFAKIND:
                return 70;
            case TWOPAIRS:
                return 60;
            case ONEPAIR:
                return 35;
            case HIGHCARD:
                return 0;
            default:
                return 0;
        }
    }

    /**
     * Makes a list of cards in your hand + cards on the table and sorts them.
     * Should be used when calculating values.
     * @return cards on table + hand.
     */
    private List<Card> getCardsFromTableAndHand(PokerTable table, Player bot) {
        ArrayList<Card> cards = new ArrayList<Card>();
        if (bot.getCard1() != null && bot.getCard2() != null) {
            cards.add(bot.getCard1());
            cards.add(bot.getCard2());
        }
        if (table != null) {
            ArrayList<Card> cardsOnTable = table.getCardOnTable();
            for (Card c : cardsOnTable) {
                cards.add(c);
            }
        }
        Collections.sort(cards);
        return cards;
    }
}
