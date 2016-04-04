package org.gruppe2.ai;

import org.gruppe2.game.old.GameSession;
import org.gruppe2.game.old.Player;

public interface AIEvaluate {
    double evaluate(GameSession session, Player bot);
}
