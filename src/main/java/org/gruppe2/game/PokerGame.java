package org.gruppe2.game;

import java.util.ArrayList;
import java.util.List;

/**
 * class to play a pokergame
 */
public class PokerGame {
    private int startChips;
    private int dealer;
    private ArrayList<Player> players;
    private Table table;


    /**
     * @param startChips
     * @param smallBlind
     * @param bigBlind   Constructor for pokergame
     */
    public PokerGame(int startChips, int smallBlindAmount, int bigBlindAmount) { //parameterene startchips, smallBlind, og bigBLind er input i GUIen som brukeren skriver inn.
        this.startChips = startChips;
        this.dealer = 0;

        table = new Table();
    }

    /**
     * @param name methods for adding players to the game/table
     */
    public void addPlayer(String name, GameClient client) {
        Player newPlayer = new Player(name, startChips, client);

        players.add(newPlayer);
    }

    /**
     * @return playerlist
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * TODO: Verify: Big blind is the player after the dealer
     *
     * @return player who is big blind
     */
    public Player getPlayerBigBlind() {
        return players.get((dealer + 1) % players.size());
    }

    /**
     * TODO: Verify: Big blind is the player after the big blind
     *
     * @return player who is small blind
     */
    public Player getPlayerSmallBlind() {
        return players.get((dealer + 2) % players.size());
    }

    /**
     * initiates the values for the gui
     */
    public void startGame() {
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
    public void dealCardsToAll() {
        for (Player player : players) {
            player.setCards(table.getDeck().drawCard(), table.getDeck().drawCard());
        }
    }

    /**
     * method for rotating blinds.
     * <p>
     * Finds Big Blind and removes his blind
     * Finds small blind, makes next person at table small blind, and makes the previous smallblind Bigblind
     * this should rotatate the blinds.
     */
    public void rotateDealer() {
        dealer = (dealer + 1) % players.size();
    }

    /**
     * Method for paying blinds
     * <p>
     * removes players not ale to pay his blind. NOTE: NEED FOR UPDATING GUI?
     * <p>
     * the blinds does not increase over time for now.
     */
    public void payBlinds() {
//        getPlayerBigBlind()
//        for(Player player: players){
//            if (player.isBigBlind()){
//                if( ! player.payBlind(bigBlind)){
//                    players.remove(player);
//                    rotateBlinds();
//                }
//            }
//            if (player.isSmallBlind()){
//               if( ! player.payBlind(smallBlind)){
//                   players.remove(player);
//                   rotateBlinds();
//               }
//            }
//        }
    }

    /**
     * lets all players do their actions
     * <p>
     * NOTE: not fully functional, does not work around raising, and responding to it, would only work if everyone calls.
     * may need to be replaced by a new function or be re-made
     */
    public void playerActionsIteration() {
//        boolean allDone = false;
//        boolean wait = true;
//        Player previousPlayer = players.get(players.size() -1);
//        for(Player player: players) {
//            while (wait) {
//                if (previousPlayer.getChoice().equals(Action.WAIT) && ! allDone) {
//                    player.doAction();
//                    previousPlayer = player;
//
//                    if (players.indexOf(player) != players.size() - 1) {
//                        wait = false;
//                    } else
//                        allDone = true;
//                }
//                else if (allDone && previousPlayer.getChoice().equals(Action.WAIT)){
//                  wait = false;
//                }
//            }
//            wait = true;
//        }
    }

    /**
     * Method for drawing cards in a texas hold 'em game. NOTE: DOES NOT DISCARD ANY CARDS AS OF NOW
     */
    public void addCardsToTable() {
        //burde kanskje kunne aksesere deck med et getDeck istedet for at alle felvaribler i Table er public.
        if (table.getCommunityCards().isEmpty()) {
            table.getCommunityCards().add(table.getDeck().drawCard());
            table.getCommunityCards().add(table.getDeck().drawCard());
            table.getCommunityCards().add(table.getDeck().drawCard());
        } else {
            table.getCommunityCards().add(table.getDeck().drawCard());
        }
    }

    /**
     * Looks through all players hands and board and declears winner
     */
    public void declareWinner() {
        //todoo
    }

    /**
     * Runs through the game for one hand
     */
    public void runGame() {
        startGame();
        payBlinds();
        playerActionsIteration();
        addCardsToTable();
        playerActionsIteration();
        addCardsToTable();
        playerActionsIteration();
        addCardsToTable();
        playerActionsIteration();
        declareWinner();
        rotateDealer();
    }


}
