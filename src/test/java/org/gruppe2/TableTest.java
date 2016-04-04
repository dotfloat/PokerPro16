package org.gruppe2;

import org.gruppe2.game.Table;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class TableTest {

    Table table;

    @Before
    public void setup() {
        table = new Table();
    }

    /* Helper method */
    public int checkNumberOfCardsDrawnFromDeck(int round) {
        int numberOfCards = table.getDeck().getAvailableCards();
        table.drawCommunityCards(round);
        return numberOfCards - table.getDeck().getAvailableCards();
    }

    @Test
    public void drawCommunityCardsShouldDrawTheCorrectAmountOfCardsFromDeckTest() {
        assertEquals("Round 0 should not draw any cards", 0, checkNumberOfCardsDrawnFromDeck(0));
        assertEquals("First round 3 cards should be drawn", 3, checkNumberOfCardsDrawnFromDeck(1));
        assertEquals("Second round 1 card should be drawn", 1, checkNumberOfCardsDrawnFromDeck(2));
        assertEquals("Third round 1 card should be drawn", 1, checkNumberOfCardsDrawnFromDeck(3));
    }

    @Test
    public void drawCommunityCardsShouldAddThemToCommunityCardsOnTableTest() {
        table.drawCommunityCards(1);
        assertEquals("Cards drawn for community cards should be in getCommunityCards", 3, table.getCommunityCards().size());
        table.drawCommunityCards(2);
        assertEquals("Cards drawn for community cards should be in getCommunityCards", 4, table.getCommunityCards().size());
        table.drawCommunityCards(3);
        assertEquals("Cards drawn for community cards should be in getCommunityCards", 5, table.getCommunityCards().size());
    }

    @Test
    public void addingToPotShouldIncreasePot() {
        int basePot = table.getPot();
        for (int i = 0; i < 100; i++) {
            table.addToPot(i);
            assertEquals("Pot should be increased with the amount added to it", basePot + i, table.getPot());
            basePot = table.getPot();
        }
    }

    @Test
    public void resetPotShouldMakeThePotEmpty() {
        for (int i = 0; i < 100; i++) {
            table.addToPot(i);
        }
        table.resetPot();
        assertEquals("The pot should be 0 after being reset", 0, table.getPot());
    }


}
