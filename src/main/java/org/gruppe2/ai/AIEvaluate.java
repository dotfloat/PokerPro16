package org.gruppe2.ai;

import org.gruppe2.backend.Player;
import org.gruppe2.backend.GameSession;

public interface AIEvaluate {
    double evaluate(GameSession session, Player bot);
}
