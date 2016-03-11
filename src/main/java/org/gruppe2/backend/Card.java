package org.gruppe2.backend;

public class Card implements Comparable<Card> {


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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (faceValue != card.faceValue) return false;
        return suit == card.suit;

    }

    @Override
    public int hashCode() {
        int result = suit != null ? suit.hashCode() : 0;
        result = 31 * result + faceValue;
        return result;
    }

    /**
     * CompareTo used for sorting a list of cards. We don't care about suit only faceValue
     * @param o card to caompare
     * @return usual compareTo result
     */
    @Override
    public int compareTo(Card o) {
        if(this.equals(o))
            return 0;

        if(this.getFaceValue() < o.getFaceValue()) {
            return -1;
        }
        else if(this.getFaceValue() > o.getFaceValue()) {
            return 1;
        }
        else {
            //in sorting we don't care about suit
            return 0;
        }
    }

    @Override
    public String toString() {
        return getFaceValue() + " of " + getSuit();
    }
}
