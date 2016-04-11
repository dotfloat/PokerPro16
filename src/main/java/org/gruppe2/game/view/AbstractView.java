package org.gruppe2.game.view;

import org.gruppe2.game.model.Model;
import org.gruppe2.game.session.SessionContext;

public abstract class AbstractView<T extends Model> {
    private final SessionContext sessionContext;
    private T model;

    public AbstractView(SessionContext sessionContext, T model) {
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
