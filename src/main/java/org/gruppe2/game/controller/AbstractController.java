package org.gruppe2.game.controller;

import org.gruppe2.game.Message;
import org.gruppe2.game.event.Event;
import org.gruppe2.game.model.Model;
import org.gruppe2.game.session.Session;
import org.gruppe2.game.session.SessionContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public abstract class AbstractController  implements Controller {
    private Session session;

    @Override
    public void init() {
        for (Method method : getClass().getDeclaredMethods()) {
            if (method.getAnnotation(Message.class) == null)
                continue;

            if (!method.getReturnType().equals(Void.TYPE))
                throw new RuntimeException("Message " + method.getName() + ": messages can only return void");

            getSession().registerMessage(method.getName());
        }

        session.getContext().registerAnnotatedHandlers(this);
    }

    @Override
    public void update() {
        for (Method method : getClass().getDeclaredMethods()) {
            if (method.getAnnotation(Message.class) == null)
                continue;

            List<Object[]> argsList = getSession().getMessages(method.getName());

            for (Object[] args : argsList) {

                // TODO: Move type checking to SessionContext
                if (args.length != method.getParameterCount()) {
                    throw new RuntimeException("Message " + method.getName() + ": expected " + method.getParameterCount() + ", got " + args.length);
                }

                for (int i = 0; i < args.length; i++) {
                    Class<?> expected = method.getParameterTypes()[i];
                    Object actual = args[i];

                    if (!expected.isInstance(actual)) {
                        throw new RuntimeException("Message " + method.getName() + ", argument " + i + ": expected " + expected.getName() + ", got " + actual.getClass().getName());
                    }
                }

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

    public Session getSession() {
        return session;
    }

    public SessionContext getContext() {
        return session.getContext();
    }

    void addEvent(Event event) {
        getSession().addEvent(event);
    }

    <M extends Model> List<M> getModels(Class<M> klass) {
        return getSession().getModels(klass);
    }

    <M extends Model> M getModel(Class<M> klass) {
        return getSession().getModel(klass);
    }
}
