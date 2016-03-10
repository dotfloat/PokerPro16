package org.gruppe2.backend;

/**
 * Created by Mikal on 09.03.16.
 */
public class Player implements PlayerAction{
    private String name;
    private int chips;
    private Card card1;
    private Card card2;
    private boolean fold = false;
    private boolean isSmallBlind;
    private boolean isBigBlind;

    public Player(String name, int startChips) {
        this.name = name;
        this.chips = startChips;
    }

    /**
     * Creates a player with no name and 0 chips
     */
    public Player() {
        chips = 0;
    }

    /**
     * Sets the player to be small blind
     */
    public void setSmallBlind() {
        isSmallBlind = true;
    }

    /**
     * Sets the player to be big blind
     */
    public void setBigBlind() {
        isBigBlind = true;
    }

    /**
     * @return True if the player is small blind
     */
    public boolean isSmallBlind() {
        return isSmallBlind;
    }

    /**
     * @return True if the player is big blind
     */
    public boolean isBigBlind() {
        return isBigBlind;
    }

    /**
     * Resets player blind status
     */
    public void resetBlinds(){
        isBigBlind = false;
        isSmallBlind = false;
    }

    /**
     * Sets the players fold status
     * @param hasFolded True when the player folds, false when there is a new round
     */
    public void setFold(boolean hasFolded) {
        fold = hasFolded;
    }

    /**
     * @return True if the player has folded this round
     */
    public boolean hasFolded() {
        return fold;
    }

    /**
     * Gives cards to player overriding previous cards
     * @param c1 card to give the player
     * @param c2 the other card to give
     */
    public void giveCards(Card c1, Card c2){
        card1 = c1;
        card2 = c2;
    }

    /**
     * Add chips to the player total owned chips
     * @param chips number of chips to add, can be negative to remove chips
     */
    public void addChips(int chips){
        this.chips += chips;
    }

    /**
     * @return the number of chips this player owns
     */
    public int getChips() {
        return chips;
    }

    /**
     * @return players name
     */
    public String getName(){
        return name;
    }

    /**
     * @return first card in hand
     */
    public Card getCard1() {
        return card1;
    }

    /**
     * @return second card in hand
     */
    public Card getCard2() {
        return card2;
    }

    @Override
    public void doAction() {

    }
}
