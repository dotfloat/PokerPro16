package org.gruppe2.game.calculation;

/**
 * Created by Mikal on 12.04.2016.
 */
public enum HandType {
    ROYALFLUSH, STRAIGHTFLUSH, FOUROFAKIND, FULLHOUSE, FLUSH, STRAIGHT, THREEOFAKIND, TWOPAIRS, PAIR, HIGHCARD;

    @Override
    public String toString() {
        switch (this) {
            case ROYALFLUSH: return "Royal flush";
            case STRAIGHTFLUSH: return "Straight flush";
            case FOUROFAKIND: return "Four of kind";
            case FULLHOUSE: return "Full house";
            case FLUSH: return "Flush";
            case STRAIGHT: return "Straight";
            case THREEOFAKIND: return "Three of a kind";
            case TWOPAIRS: return "Two pairs";
            case PAIR: return "Pair";
            default: return "High card";
        }
    }
}
