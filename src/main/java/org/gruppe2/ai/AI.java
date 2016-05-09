package org.gruppe2.ai;

import org.gruppe2.game.Card;
import org.gruppe2.game.Player;
import org.gruppe2.game.PossibleActions;
import org.gruppe2.game.RoundPlayer;
import org.gruppe2.game.helper.RoundHelper;

import java.util.List;

public interface AI {
    void doAction(Player player, RoundPlayer roundPlayer, RoundHelper roundHelper);
}
