package org.gruppe2.ai;

import org.gruppe2.backend.GameSession;
import org.gruppe2.backend.Player;

public interface AIEvaluate {
    double evaluate(GameSession session, Player bot);
}
