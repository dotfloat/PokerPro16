package org.gruppe2.ai;

import org.gruppe2.backend.Player;
import org.gruppe2.backend.PokerTable;

public interface AIEvaluate {
    double evaluate(PokerTable table, Player bot);
}
