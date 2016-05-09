package org.gruppe2.ai;

import org.gruppe2.game.Card;
import org.gruppe2.game.Player;
import org.gruppe2.game.PossibleActions;
import org.gruppe2.game.RoundPlayer;

import java.util.List;

public interface AI {
    void doAction(Player player, RoundPlayer roundPlayer, PossibleActions options, List<Card> communityCards);
}
