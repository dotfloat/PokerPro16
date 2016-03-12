package org.gruppe2.backend;

import java.util.List;

public class GameInit {
    List<Player> players;
    Board board;

    /**
     * New game
     * @param players list of players with reference to the game client
     * @param board board we're playing on
     */
    public GameInit(List<Player> players, Board board) {
        this.players = players;
        this.board = board;
    }

    /**
     * Main game loop
     * Notify players about which players turn it is, waits for that players action and notifies all players about the action
     */
    void runGame() {
        for(Player player : players) {
            notifyOtherPlayersAboutTurn(player);
            Action playerAction = player.getClient().onTurn(board);
            doPlayerAction(playerAction, player);
            notifyOtherPlayersAboutAction(player, playerAction);

        }
    }

    /**
     * Notify all players about whose turn it is
     * @param player player whose turn it is
     */
    void notifyOtherPlayersAboutTurn(Player player) {
        for(Player playerToNotify : players) {
            if(!playerToNotify.equals(player)) {
                playerToNotify.getClient().onOtherPlayerTurn(board, player);
            }
        }
    }

    /**
     * Notify all players abut the action performed
     * @param player player who performed the action
     * @param action the action performed
     */
    void notifyOtherPlayersAboutAction(Player player, Action action) {
        for(Player playerToNotify : players) {
            if(!playerToNotify.equals(player)) {
                playerToNotify.getClient().onOtherPlayerAction(board, player, action);
            }
        }
    }

    //TODO: Code to perform actions
    /**
     * Perform the action requested by the player
     * @param action action to perform
     * @param player player performing
     */
    void doPlayerAction(Action action, Player player) {
        if(checkLegalAction(action, player)) {
            //do action something
        }
        else {
            //no legal action
        }
    }

    //TODO: Check for legal action
    /**
     * Check if it is a legal action
     * @param action action being performed
     * @param player player performing
     * @return true if it's legal, false if not
     */
    boolean checkLegalAction(Action action, Player player) {
        return true;
    }
}
