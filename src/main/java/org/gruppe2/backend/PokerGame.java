package org.gruppe2.backend;

import org.gruppe2.frontend.GUI;
import org.gruppe2.frontend.InitializeGame;

import java.util.ArrayList;

/**
 * class to play a pokergame
 */
public class PokerGame{

    GUI gui;
    int startChips;
    int smallBlind;
    int bigBlind;

    private ArrayList<Player> players;
    private PokerTable table;


    /**
     *
     * @param gui
     * @param startChips
     * @param smallBlind
     * @param bigBlind
     *
     * Constructor for pokergame
     */
    public PokerGame(GUI gui, int startChips, int smallBlind, int bigBlind){ //parameterene startchips, smallBlind, og bigBLind er input i GUIen som brukeren skriver inn.
        this.gui = gui;
        this.smallBlind = smallBlind;
        this.bigBlind = bigBlind;
        this.startChips = startChips;

         table = new PokerTable(new Deck(), 0);
    }

    /**
     * @param name
     *
     * methods for adding players to the game/table
     */
    public void addPlayer(String name){
        Player newPlayer = new Player(name, startChips, table);
        players.add(newPlayer);
        table.addPlayer(newPlayer);
    }

    /**
     * @return playerlist
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * initiates the values for the gui
     */
    public void startGame(){
        /* need to refresh methods in InitializeGame and gui for these to work
        InitializeGame.setStartValues(this);
        InitializeGame.setPlayersToTable(this, gui);
        dealCardsToAll();
        gui.getMainFrame().showCardsOnHand(players);
        */
    }

    /**
     * Deals two cards to each player
     */
    public void dealCardsToAll(){
        for(Player guy: players){
            guy.giveCards(table.deck.drawCard(), table.deck.drawCard());
        }
    }

    /**
     * lets all players do their actions
     */
    public void playerActionsIteration(){
        boolean wait = true;
        Player previousPlayer = players.get(players.size() -1);
        for(Player player: players){
            while(wait) {
                if (previousPlayer.getChoice().equals(Action.WAIT)) {
                    player.doAction();
                    previousPlayer = player;
                    wait = false;
                }
            }
            wait = true;
        }
    }

    /**
     * Method for drawing cards in a texas hold 'em game. NOTE: DOES NOT DISCARD ANY CARDS AS OF NOW
     */
    public void addCardsToTable(){
        //burde kanskje kunne aksesere deck med et getDeck istedet for at alle felvaribler i PokerTable er public.
        if(table.getCardOnTable().isEmpty()){
            table.getCardOnTable().add(table.deck.drawCard());
            table.getCardOnTable().add(table.deck.drawCard());
            table.getCardOnTable().add(table.deck.drawCard());
        }
        else {
            table.getCardOnTable().add(table.deck.drawCard());
        }
    }

    /**
     * Looks through all players hands and board and declears winner
     */
    public void declareWinner(){
        //todoo
    }

    /**
     * Runs through the game for one hand
     */
    public void runGame(){
        startGame();
        playerActionsIteration();
        addCardsToTable();
        playerActionsIteration();
        addCardsToTable();
        playerActionsIteration();
        addCardsToTable();
        playerActionsIteration();
        declareWinner();
    }





    


}
