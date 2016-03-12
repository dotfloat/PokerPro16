package org.gruppe2;

import org.gruppe2.backend.Card;
import org.gruppe2.backend.Player;

import java.util.List;

@SuppressWarnings("UnusedParameters")
public interface GameClient {
    /* Session events */

    /**
     * Called when another player connects
     * @param board
     * @param otherPlayer
     */
    default void onPlayerConnect(Board board, Player otherPlayer) {}

    /**
     * Called when another player disconnects
     * @param board
     * @param otherPlayer
     */
    default void onPlayerDisconnect(Board board, Player otherPlayer) {}

    /* Game events */

    /**
     * Called after the round starts
     * @param board
     */
    default void onRoundStart(Board board) {}

    /**
     * Called before the round ends
     * @param board
     */
    default void onRoundEnd(Board board) {}

    /**
     * Called when it's the player's action. It is allowed to block.
     *
     * Only active players can receive this. (Not spectators)
     *
     * @param board
     * @return The action that the client wants to do
     */
    default Action onTurn(Board board) { return Action.DISCONNECT; }

    /**
     * Called before the player has decided on an action.
     * @param board
     * @param otherPlayer
     */
    default void onOtherPlayerTurn(Board board, Player otherPlayer) {}

    /**
     * Called after the player has decided on an action.
     * @param board
     * @param otherPlayer
     * @param action
     */
    default void onOtherPlayerAction(Board board, Player otherPlayer, Action action) {}

    /**
     * Called when cards are added to the communal cards.
     * @param board
     * @param newCards
     */
    default void onCommunalCards(Board board, List<Card> newCards) {}
}

enum Action {
    DISCONNECT
}

class Board {
}
