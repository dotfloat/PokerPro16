package org.gruppe2.backend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GameSession {
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Player> activePlayers = new ArrayList<>();
    private Table table = new Table();
    private int smallBlindAmount = 5;
    private int bigBlindAmount = 10;

    public int getSmallBlindAmount() {
        return smallBlindAmount;
    }

    public int getBigBlindAmount() {
        return bigBlindAmount;
    }

    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Main game loop
     * Notify players about which players turn it is, waits for that players action and notifies all players about the action
     */
    public void mainLoop() {
        for (;;) {
            roundLoop();
        }
    }

    public void addPlayer(String name, GameClient client) {
        Player player = new Player(name, 100, client);
        players.add(player);
    }

    private void roundLoop() {
        activePlayers = new ArrayList<>();
        activePlayers.addAll(players);

        notifyRoundStart();

        for (int current = 0; !activePlayers.isEmpty(); current++) {
            int currentPlayerIdx = (current + 1) % activePlayers.size();
            Player player = activePlayers.get(currentPlayerIdx);

            if (player == null)
                continue;

            if (current == 0) {
                player.setBet(bigBlindAmount);
                notifyOtherPlayersAboutAction(player, new Action(Action.Type.BIG_BLIND, bigBlindAmount));
                continue;
            } else if (current == 1) {
                player.setBet(smallBlindAmount);
                notifyOtherPlayersAboutAction(player, new Action(Action.Type.SMALL_BLIND, smallBlindAmount));
                continue;
            }

            notifyOtherPlayersAboutTurn(player);
            Action action = player.getClient().onTurn();
            notifyOtherPlayersAboutAction(player, action);

            if (action.getType() == Action.Type.FOLD) {
                activePlayers.set(currentPlayerIdx, null);
            }

            if (numActivePlayers() == 1) {
                for (Player p : activePlayers) {
                    if (p != null) {
                        notifyPlayerVictory(p);
                        break;
                    }
                }
                break;
            }
        }

        notifyRoundEnd();
    }

    private int numActivePlayers() {
        int numActivePlayers = 0;

        for (Player p : activePlayers) {
            if (p != null) {
                numActivePlayers++;
            }
        }

        return numActivePlayers;
    }

    void notifyRoundStart() {
        for (Player p : players) {
            p.getClient().onRoundStart();
        }
    }

    void notifyRoundEnd() {
        for (Player p : players) {
            p.getClient().onRoundEnd();
        }
    }

    void notifyPlayerVictory(Player winner) {
        for (Player p : players) {
            p.getClient().onPlayerVictory(winner);
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

    public Table getTable() {
        return table;
    }
}
