package org.gruppe2.game.session;

import org.gruppe2.game.event.Event;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Future;

public class SessionContext {
    private final Session session;

    private final ConcurrentEventQueue eventQueue = new ConcurrentEventQueue();

    SessionContext(Session session) {
        this.session = session;

        session.getEventQueue().addQueue(eventQueue);
    }

    public ConcurrentEventQueue getEventQueue() {
        return eventQueue;
    }

    public <M> M getModel(Class<M> klass) {
        return (M) session.getModel(klass);
    }

    public void addEvent(Event event) {
        session.getEventQueue().addEvent(event);
    }

    public void sendMessage(String name, Object... args) {
        session.sendMessage(name, args);
    }

    public Future<Boolean> message(String name, Object... args) {
        return session.sendMessage(name, args);
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

    public void setAnnotated(Object obj) {
        setAnnotatedModels(obj);
        setAnnotatedHelpers(obj);
        setAnnotatedHandlers(obj);
    }

    public void setAnnotatedModels(Object obj) {
        for (Field f : obj.getClass().getDeclaredFields()) {
            if (f.getAnnotation(Model.class) == null)
                continue;

            try {
                Class<?> klass = f.getDeclaringClass();
                Constructor<?> ctor = klass.getConstructor(SessionContext.class);

                f.set(obj, ctor.newInstance(this));
            } catch (NoSuchMethodException e) {
                System.out.printf("Field %s: must be a model", f.getName());
                e.printStackTrace();
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public void setAnnotatedHelpers(Object obj) {
        for (Field f : obj.getClass().getDeclaredFields()) {
            if (f.getAnnotation(Helper.class) == null)
                continue;

            try {
                Class<?> type = f.getType();
                Constructor<?> ctor = type.getConstructor(SessionContext.class);

                f.setAccessible(true);
                f.set(obj, ctor.newInstance(this));
            } catch (NoSuchMethodException e) {
                System.err.printf("Field %s in %s: must be a helper\n", f.getName(), obj.getClass().getSimpleName());
                e.printStackTrace();
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public void setAnnotatedHandlers(Object obj) {
        for (Method m : obj.getClass().getDeclaredMethods()) {
            if (m.getAnnotation(Handler.class) == null)
                continue;

            if (m.getParameterCount() != 1)
                throw new RuntimeException("Handler " + m.getName() + " can only take one argument");

            if (!m.getReturnType().equals(Void.TYPE))
                throw new RuntimeException(String.format("Handler %s return type: expected void, got %s", m.getName(), m.getReturnType()));

            Class<?> eventClass = m.getParameterTypes()[0];

            if (eventClass.isInstance(Event.class))
                throw new RuntimeException("Handler " + m.getName() + " must handle an Event");

            m.setAccessible(true);

            getEventQueue().registerHandler(eventClass, (Event event) -> m.invoke(obj, event));
        }
    }

    /**
     * Create a brand new context for use in new threads
     *
     * @return A
     */
    public SessionContext createContext() {
        return new SessionContext(session);
    }
}
