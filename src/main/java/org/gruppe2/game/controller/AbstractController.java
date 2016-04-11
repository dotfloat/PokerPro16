package org.gruppe2.game.controller;

import com.sun.istack.internal.NotNull;
import org.gruppe2.game.model.Model;
import org.gruppe2.game.session.SessionContext;

abstract class AbstractController<T extends Model> {
    private final SessionContext sessionContext;
    private T model;

    public AbstractController(SessionContext sessionContext, T model) {
        this.sessionContext = sessionContext;
        this.model = model;
    }

    SessionContext getSessionContext() {
        return sessionContext;
    }

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }
}
