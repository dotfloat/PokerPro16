package org.gruppe2.game.view;

import org.gruppe2.game.model.Model;
import org.gruppe2.game.session.SessionContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class AbstractView<M extends Model> implements View {
    private SessionContext context;

    @Override
    public void setContext(SessionContext context) {
        this.context = context;
    }

    @Override
    public SessionContext getContext() {
        return context;
    }
}
