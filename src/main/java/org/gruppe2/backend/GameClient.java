package org.gruppe2.backend;

import java.util.List;

@SuppressWarnings("UnusedParameters")
public class GameClient {
    private GameSession session = null;

    public GameClient() {

    }

    public GameClient(GameSession session) {
        this.session = session;
    }

    /* Session events */

    /**
     * Called when another player connects
     * @param board
     * @param otherPlayer
     */
    public void onPlayerConnect(Player otherPlayer) {}

    /**
     * Called when another player disconnects
     * @param board
     * @param otherPlayer
     */
    public void onPlayerDisconnect(Player otherPlayer) {}

    /* Game events */

    /**
     * Called after the round starts
     * @param board
     */
    public void onRoundStart() {}

    /**
     * Called before the round ends
     * @param board
     */
    public void onRoundEnd() {}

    /**
     * Called when a player wins
     */
    public void onPlayerVictory(Player player) {

    }

    /**
     * Called when it's the player's action. It is allowed to block.
     *
     * Only active players can receive this. (Not spectators)
     *
     * @param board
     * @return The action that the client wants to do
     */
    public Action onTurn(Player player) { return null; }

    /**
     * Called before the player has decided on an action.
     * @param board
     * @param otherPlayer
            return numChips;
     */
    public void onOtherPlayerTurn(Player otherPlayer) {}

    /**
     * Called after the player has decided on an action.
     * @param board
     * @param otherPlayer
     * @param action
     */
    public void onOtherPlayerAction(Player otherPlayer, Action action) {}

    /**
     * Called after the player has decided on an action.
     * @param player
     * @param action
     */
    public void onPlayerAction(Player player, Action action){}

    /**
     * Called when cards are added to the communal cards.
     * @param board
     * @param newCards
     */
    public void onCommunalCards(List<Card> newCards) {}

    public GameSession getSession() {
        return session;
    }

    public void setSession(GameSession session) {
        this.session = session;
    }
}
