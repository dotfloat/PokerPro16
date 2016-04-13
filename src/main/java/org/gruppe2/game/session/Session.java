package org.gruppe2.game.session;

import org.gruppe2.game.controller.Controller;
import org.gruppe2.game.event.Event;
import org.gruppe2.game.model.Model;

import java.util.*;

public abstract class Session implements Runnable {
    private final MainEventQueue eventQueue = new MainEventQueue();

    private final Map<Class<? extends Model>, List<Model>> modelMap = Collections.synchronizedMap(new HashMap<>());
    private final Map<Class<? extends Controller>, Controller> controllerMap = Collections.synchronizedMap(new HashMap<>());
    private final Map<String, List<Object[]>> messageMap = Collections.synchronizedMap(new HashMap<>());

    private final SessionContext sessionContext = new SessionContext(this);

    private volatile boolean ready = false;

    public static SessionContext start(Class<? extends Session> klass) {
        Session session;

        try {
            session = klass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }

        return session.start();
    }

    public SessionContext start() {
        new Thread(this).start();

        return new SessionContext(this);
    }

    @Override
    public void run() {
        init();

        controllerMap.values().forEach(Controller::init);

        ready = true;

        while (true) {

            eventQueue.process();

            update();

            controllerMap.values().forEach(Controller::update);


            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public abstract void init();

    public abstract void update();

    MainEventQueue getEventQueue() {
        return eventQueue;
    }

    public <M extends Model> void addModels(Class<M> klass) {
        modelMap.put(klass, Collections.synchronizedList(new ArrayList<>()));
    }

    public <C extends Controller> void addController(Class<C> klass) {
        C controller = null;

        try {
            controller = klass.newInstance();
            controller.setSession(this);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        controllerMap.put(klass, controller);
    }

    public <M extends Model> List<M> getModels(Class<M> klass) {
        return (List<M>) modelMap.get(klass);
    }

    public <M extends Model> M getModel(Class<M> klass) {
        List<Model> list = modelMap.get(klass);

        return list != null ? (M) list.get(0) : null;
    }

    public abstract void exit();

    // TODO: Move these to a "GameController" or something

    public SessionContext getSessionContext() {
        return sessionContext;
    }

    public void registerMessage(String name) {
        if (messageMap.containsKey(name))
            throw new RuntimeException("Message by the name of " + name + " is already registered.");

        messageMap.put(name, Collections.synchronizedList(new ArrayList<>()));
    }

    /**
     * Add a message to a message queue
     * @param name the name of the message
     * @param args message arguments
     */
    public void addMessage(String name, Object... args) {
        List<Object[]> list = messageMap.get(name);

        list.add(args);
    }

    /**
     * Get a list of messages for a given message
     * @param name the name of the message
     * @return a list of messages
     * @throws NullPointerException if method doesn't exist
     */
    public List<Object[]> getMessages(String name) {
        List<Object[]> list = messageMap.get(name);
        List<Object[]> out = new ArrayList<>(list);

        list.clear();

        return out;
    }

    public void addEvent(Event event) {
        eventQueue.addEvent(event);
    }

    public boolean isReady() {
        return ready;
    }
}
