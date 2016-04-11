package org.gruppe2.game.controller;

import org.gruppe2.game.model.Model;
import org.gruppe2.game.session.SessionContext;
import org.gruppe2.game.view.AbstractView;

public abstract class AbstractController<M extends Model, V extends AbstractView<?>> {
    private final SessionContext sessionContext;
    private M model = null;
    private V view = null;

    public AbstractController(SessionContext sessionContext) {
        this.sessionContext = sessionContext;
    }

    public SessionContext getSessionContext() {
        return sessionContext;
    }

    public M getModel() {
        return model;
    }

    public void setModel(M model) {
        this.model = model;
    }

    public V getView() {
        return view;
    }

    public void setView(V view) {
        this.view = view;
    }
}
