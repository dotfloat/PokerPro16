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
     *
     * @param board
     */
    default void onRoundStart(Board board) {}
    default void onRoundEnd(Board board) {}

    default Action onTurn(Board board) { return null; }
    default void onOtherPlayerTurn(Board board, Player otherPlayer) {}
    default void onOtherPlayerAction(Board board, Player otherPlayer, Action action) {}

    default void onCommunalCards(Board board, List<Card> newCards) {}
}

public class Action {
}

public class Board {
}
