package org.gruppe2.ai;

import org.gruppe2.backend.Action;
import org.gruppe2.backend.GameClient;
import org.gruppe2.backend.GameSession;

public class AIClient extends GameClient {

    public AIClient(GameSession session) {
        super(session);
    }

    @Override
    public Action onTurn() {
        return new Action(Action.Type.FOLD); // Best AI
    }
}
