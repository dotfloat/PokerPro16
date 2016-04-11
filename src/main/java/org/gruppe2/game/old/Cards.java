package org.gruppe2.game.old;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Cards {

    public static List asList(String cards){
        List<Card> cardList = new ArrayList<Card>();

        String[] individualCards = cards.split("\\s+");

        boolean error = false;

        for(int i = 0; i < individualCards.length; i++){
            int value = 0;
            Card.Suit suit = Card.Suit.HEARTS;

            switch(individualCards[i].charAt(0)){

                case 'A':  value = Card.ACE;
                    break;
                case '2': value = 2;
                    break;
                case '3': value = 3;
                    break;
                case '4': value = 4;
                    break;
                case '5': value = 5;
                    break;
                case '6': value = 6;
                    break;
                case '7': value = 7;
                    break;
                case '8': value = 8;
                    break;
                case '9': value = 9;
                    break;
                case '1': value = 10;
                    break;
                case 'J': value = Card.JACK;
                    break;
                case 'Q': value = Card.QUEEN;
                    break;
                case 'K': value = Card.KING;
                    break;
                default: System.out.println("weird value of card " +i+1 + ".");
                    error = true;
                    break;
            }
            switch(individualCards[i].charAt(1)){
                case 'D': suit = Card.Suit.DIAMONDS;
                    break;
                case 'C': suit = Card.Suit.CLUBS;
                    break;
                case 'H': suit = Card.Suit.HEARTS;
                    break;
                case 'S': suit = Card.Suit.SPADES;
                    break;
                default: System.out.println("Weird suit of card " +(i+1) + ". received input: " +individualCards[i].charAt(1)+ ". If your value is 10, use 1(10 Hearths = 1H)");
                    error = true;
                    break;
            }

            if(!error){
                Card card = new Card(value, suit);
                cardList.add(card);
            }else break;


        }
        if(!error)
            return cardList;

        return null;
    }
}
