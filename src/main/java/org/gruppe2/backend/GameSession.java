package org.gruppe2.backend;

import java.util.ArrayList;

public class GameSession {
    ArrayList<Player> players = new ArrayList<>();
    Table table = new Table();

    /**
     * Main game loop
     * Notify players about which players turn it is, waits for that players action and notifies all players about the action
     */
    public void mainLoop() {
        for (;;) {
            eachTurn();
        }
    }

    public void addPlayer(String name, GameClient client) {
        Player player = new Player(name, 100, client);
        players.add(player);
    }

    private void eachTurn() {
        for(Player player : players) {
            notifyOtherPlayersAboutTurn(player);
            Action playerAction = player.getClient().onTurn();
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
                playerToNotify.getClient().onOtherPlayerTurn(player);
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
                playerToNotify.getClient().onOtherPlayerAction(player, action);
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
        if (checkLegalAction(action, player)) {
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
