package org.gruppe2.backend;

public class Card {
    public enum Suit {CLUBS, DIAMONDS, HEARTS, SPADES}

    private Suit suit;
    private int faceValue;

    public Card(int faceValue, Suit suit) {
        if(faceValue < 2 || faceValue > 14) {
            throw new IllegalArgumentException("faceValue can't be less than 2 or bigger than 13");
        }
        else {
            this.faceValue = faceValue;
            this.suit = suit;
        }
    }

    public Suit getSuit() {
        return suit;
    }

    public int getFaceValue() {
        return faceValue;
    }

    @Override
    public String toString() {
        return getFaceValue() + " of " + getSuit();
    }
}
