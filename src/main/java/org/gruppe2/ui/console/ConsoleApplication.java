package org.gruppe2.ui.console;

import org.gruppe2.game.session.GameSession;
import org.gruppe2.game.session.Session;
import org.gruppe2.game.session.SessionContext;

public class ConsoleApplication implements Runnable {
    private SessionContext sessionContext;

    @Override
    public void run() {
        sessionContext = Session.start(GameSession.class);
    }

    public SessionContext getSessionContext() {
        return sessionContext;
    }
}
