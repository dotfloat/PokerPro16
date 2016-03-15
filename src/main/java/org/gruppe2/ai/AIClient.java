package org.gruppe2.ai;

import org.gruppe2.backend.Action;
import org.gruppe2.backend.GameClient;
import org.gruppe2.backend.GameSession;
import org.gruppe2.backend.Player;

public class AIClient extends GameClient {

    public AIClient(GameSession session) {
        super(session);
    }

    @Override
    public Action onTurn(Player player) {
        return new Action.Fold(); // Best AI
    }
}
