package org.gruppe2.game.session;

import org.gruppe2.game.event.Event;
import org.gruppe2.game.model.Model;
import org.gruppe2.game.model.PlayerModel;
import org.gruppe2.game.view.View;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionContext {
    private final Session session;

    private final ConcurrentEventQueue eventQueue = new ConcurrentEventQueue();
    private final Map<Class<? extends View>, View> viewMap = Collections.synchronizedMap(new HashMap<>());

    SessionContext(Session session) {
        this.session = session;

        session.getEventQueue().registerQueue(eventQueue);
    }

    public ConcurrentEventQueue getEventQueue() {
        return eventQueue;
    }

    public <M extends Model> List<M> getModels(Class<M> klass) {
        return session.getModels(klass);
    }

    public <M extends Model> M getModel(Class<M> klass) {
        return session.getModel(klass);
    }

    public <V extends View> V getView(Class<V> klass) {
        View view = viewMap.get(klass);

        if (view != null)
            return (V) view;

        try {
            view = klass.newInstance();
            view.setContext(this);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        viewMap.put(klass, view);

        return (V) view;
    }

    public <E extends Event, M extends Model> void addEvent(Class<E> klass, M model, E event) {
        session.getEventQueue().addEvent(klass, model, event);
    }

    public <E extends Event> void addEvent(Class<E> klass, E event) {
        session.getEventQueue().addEvent(klass, event);
    }

    public void message(String name, Object... args) {
        session.addMessage(name, args);
    }

    public void waitReady() {
        while (!session.isReady()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
