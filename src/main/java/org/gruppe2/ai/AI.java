package org.gruppe2.ai;

import org.gruppe2.game.Player;
import org.gruppe2.game.RoundPlayer;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.helper.RoundHelper;

public interface AI {
    void doAction(Player player, RoundPlayer roundPlayer, RoundHelper roundHelper, GameHelper gameHelper);
}
