package org.gruppe2.frontend;

/**
 * Created by Mikal on 09.03.16.
 */
public class Player{
    private String name;
    private int chips;
    private Card card1;
    private Card card2;
    private boolean fold = false;
    private boolean isSmallBlind;
    private boolean isBigBlind;
    private PokerTable table;
    private boolean isBot;
    private Action choice = Action.WAITING;

    /**
     * Makes a player which is a user player
     * @param name Player name
     * @param startChips How many starting chips the player starts with
     * @param table The table the player plays on
     */
    public Player(String name, int startChips, PokerTable table) {
        this.name = name;
        this.chips = startChips;
        this.table = table;
        this.isBot = false;
    }

    /**
     * Makes a player controlled by a AI
     * @param startChips How many chips a player starts with
     * @param table The table the AI plays on
     */
    public Player(String name, int startChips, PokerTable table,boolean isbot) {
    	this.name = name;
    	this.chips = startChips;
        this.table = table;
        this.isBot = true;
    }

    /**
     * @return True if this player is controlled by an AI
     */
    public boolean isBot() {
        return isBot;
    }

    /**
     * Sets the player to be small blind
     */
    public void setSmallBlind() {
        if (isBigBlind)
            isBigBlind = false;
        isSmallBlind = true;
    }

    /**
     * Sets the player to be big blind
     */
    public void setBigBlind() {
        if (isSmallBlind)
            isSmallBlind = false;
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

    
    public void doAction(Action choice) {
        this.choice = choice;
    	switch (choice)
        {
        	case CALL:
        		
        	case CHECK:
        		if(table.getPlayers().get(0) == this){} //Do nothing
        		
        		else{
        			pay(50); //Just something to make the program run
        		}
        		
        	case FOLD:
        		
        	case RAISE:
        		
        	default: //Finished
        		
        }
    }

    /**
     * @return The last play choice
     */
    public Action getChoice() {
        return choice;
    }

    /**
     * Makes the player interact with the table
     * @param action The action to be preformed by the player
     */
    public void play (Action action){
        choice = action;
    }
    public void pay(int amount) {
		chips = chips - amount;
		table.addToPot(amount);
	}
    
    @Override
    public String toString(){
    	return name;
    }
}
