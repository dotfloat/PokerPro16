package org.gruppe2.game.controller;

import org.gruppe2.game.model.Model;
import org.gruppe2.game.session.SessionContext;

abstract class AbstractController<T extends Model> {
    private SessionContext sessionContext = null;
    private T model;

    public AbstractController(T model) {
        this.model = model;
    }

    void update() {

    }

    SessionContext getSessionContext() {
        return sessionContext;
    }

    void setSessionContext(SessionContext sessionContext) {
        this.sessionContext = sessionContext;
    }

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }
}
