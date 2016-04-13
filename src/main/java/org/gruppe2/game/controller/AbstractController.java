package org.gruppe2.game.controller;

import org.gruppe2.game.SMessage;
import org.gruppe2.game.event.Event;
import org.gruppe2.game.model.Model;
import org.gruppe2.game.session.Session;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractController<M extends Model>  implements Controller {
    private Session session;

    @Override
    public void init() {
        for (Method method : getClass().getDeclaredMethods()) {
            if (method.getAnnotation(SMessage.class) == null)
                continue;

            getSession().registerMessage(method.getName());
        }
    }

    @Override
    public void update() {
        for (Method method : getClass().getDeclaredMethods()) {
            if (method.getAnnotation(SMessage.class) == null)
                continue;

            List<Object[]> argsList = getSession().getMessages(method.getName());

            for (Object[] args : argsList) {
                try {
                    method.invoke(this, args);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

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
