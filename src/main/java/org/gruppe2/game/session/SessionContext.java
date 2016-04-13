package org.gruppe2.game.session;

import org.gruppe2.game.Handler;
import org.gruppe2.game.event.Event;
import org.gruppe2.game.model.Model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionContext {
    private final Session session;

    private final ConcurrentEventQueue eventQueue = new ConcurrentEventQueue();

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

    public void addEvent(Model model, Event event) {
        session.getEventQueue().addEvent(model, event);
    }

    public void addEvent(Event event) {
        session.getEventQueue().addEvent(event);
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

    public void registerAnnotatedHandlers(Object object) {
        for (Method method : object.getClass().getDeclaredMethods()) {
            if (method.getAnnotation(Handler.class) == null)
                continue;

            if (method.getParameterCount() != 1)
                throw new RuntimeException("Handler " + method.getName() + " can only take one argument");

            Class<?> eventClass = method.getParameterTypes()[0];

            if (eventClass.isInstance(Event.class))
                throw new RuntimeException("Handler " + method.getName() + " must handle an Event");

            method.setAccessible(true);

            getEventQueue().registerHandler(eventClass, (Event event) -> {
                try {
                    method.invoke(object, event);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    System.out.println(e.getTargetException());
                    e.printStackTrace();
                }
            });
        }
    }
}
