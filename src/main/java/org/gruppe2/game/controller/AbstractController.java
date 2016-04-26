package org.gruppe2.game.controller;

import org.gruppe2.game.event.Event;
import org.gruppe2.game.session.Session;
import org.gruppe2.game.session.SessionContext;

abstract class AbstractController  implements Controller {
    private Session session;

    @Override
    public void init() {

    }

    @Override
    public void update() {

    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    Session getSession() {
        return session;
    }

    SessionContext getContext() {
        return session.getContext();
    }

    void addEvent(Event event) {
        getContext().addEvent(event);
    }

    <M> M getModel(Class<M> klass) {
        return getContext().getModel(klass);
    }
}
