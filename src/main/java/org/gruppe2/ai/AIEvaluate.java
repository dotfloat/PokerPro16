package org.gruppe2.ai;

import org.gruppe2.game.GameSession;
import org.gruppe2.game.Player;

public interface AIEvaluate {
    double evaluate(GameSession session, Player bot);
}
