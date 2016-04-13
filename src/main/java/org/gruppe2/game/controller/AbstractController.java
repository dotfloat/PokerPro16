package org.gruppe2.game.controller;

import org.gruppe2.game.event.Event;
import org.gruppe2.game.model.Model;
import org.gruppe2.game.session.Session;
import org.gruppe2.game.session.SessionContext;
import org.gruppe2.game.view.AbstractView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public abstract class AbstractController<M extends Model>  implements Controller {
    private Session session;

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public Session getSession() {
        return session;
    }

    public M getModel() {
        return (M) session.getModel(getModelClass());
    }

    public List<M> getModels() {
        return (List<M>) session.getModels(getModelClass());
    }

    public <E extends Event> void addEvent(Class<E> klass, E event) {
        getSession().addEvent(klass, event);
    }
}
