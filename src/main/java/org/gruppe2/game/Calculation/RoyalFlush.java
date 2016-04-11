package org.gruppe2.game.Calculation;

import org.gruppe2.game.old.Card;
import org.gruppe2.game.old.Player;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Mikal on 11.04.2016.
 */
public class RoyalFlush {

    public boolean canGetRoyalFlush(ArrayList<Card> communityCards, Player p){
        if (communityCards.size() == 0)
            return true;

        ArrayList<Card> allCards = new ArrayList<>(communityCards);
        allCards.add(p.getCard1());
        allCards.add(p.getCard2());

        if (numberOfRoyalCards(allCards) >= communityCards.size()){
            HashMap<Card.Suit, Integer> numTypes = new HashMap<>();
            numTypes.put(Card.Suit.CLUBS, 0);
            numTypes.put(Card.Suit.DIAMONDS, 0);
            numTypes.put(Card.Suit.SPADES, 0);
            numTypes.put(Card.Suit.HEARTS, 0);

            for(Card c : allCards)
                if (cardIsRoyal(c))
                    numTypes.put(c.getSuit(), numTypes.get(c.getSuit()) + 1);

            for (int i : numTypes.values())
                if (i >= communityCards.size())
                    return true;
        }


        return false;
    }

    private int numberOfRoyalCards(ArrayList<Card> allCards){
        int royalCards = 0;
        for (Card c : allCards)
            if (cardIsRoyal(c))
                royalCards++;
        return royalCards;
    }

    private boolean cardIsRoyal(Card c) {
        return c.getFaceValue() >= 10 || c.getFaceValue() <= 14;
    }

    public Double royalFlushPossiblity(ArrayList<Card> communityCards, Player p) {
        if (canGetRoyalFlush(communityCards, p))
            return 0.0;

        ArrayList<Card> royalFlush = new ArrayList<>();
    }
}
