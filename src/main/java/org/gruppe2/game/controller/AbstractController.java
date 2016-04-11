package org.gruppe2.game.controller;

import org.gruppe2.game.session.AsyncStatus;
import org.gruppe2.game.session.SessionContext;

abstract class AbstractController {
    private SessionContext sessionContext = null;
    private AsyncStatus asyncStatus = AsyncStatus.WAITING;

    void update() {

    }

    SessionContext getSessionContext() {
        return sessionContext;
    }

    void setSessionContext(SessionContext sessionContext) {
        this.sessionContext = sessionContext;
    }

    public AsyncStatus getAsyncStatus() {
        return asyncStatus;
    }

    public void setAsyncStatus(AsyncStatus asyncStatus) {
        this.asyncStatus = asyncStatus;
    }
}
