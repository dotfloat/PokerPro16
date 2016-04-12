package org.gruppe2.game.controller;

import org.gruppe2.game.model.Model;
import org.gruppe2.game.session.SessionContext;
import org.gruppe2.game.view.AbstractView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class Controller<M extends Model, V extends AbstractView<M>> {
    private final SessionContext sessionContext;

    public Controller(SessionContext sessionContext) {
        this.sessionContext = sessionContext;
    }

    public SessionContext getSessionContext() {
        return sessionContext;
    }

    public abstract void init();

    public abstract void update();

    public abstract M getModel();

    public abstract V getView();
}
