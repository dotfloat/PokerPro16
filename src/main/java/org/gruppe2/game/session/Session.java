package org.gruppe2.game.session;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.gruppe2.game.controller.Controller;
import org.gruppe2.game.event.Event;
import org.gruppe2.game.model.Model;
import org.gruppe2.game.model.PlayerModel;
import org.gruppe2.game.view.View;

import java.util.*;

public abstract class Session implements Runnable {
    private final MainEventQueue eventQueue = new MainEventQueue();

    private final Map<Class<? extends Model>, List<Model>> modelMap = Collections.synchronizedMap(new HashMap<>());
    private final Map<Class<? extends Controller>, Controller> controllerMap = Collections.synchronizedMap(new HashMap<>());
    private final Map<String, List<Object>> messageMap = Collections.synchronizedMap(new HashMap<>());

    private final SessionContext sessionContext = new SessionContext(this);

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
        while (true) {

            eventQueue.process();

            update();

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        controllerMap.values().forEach(Controller::update);
    }

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

        controller.init();
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

    public void addMessage(String name, Object object) {
        List<Object> list = messageMap.get(name);

        if (list == null) {
            list = Collections.synchronizedList(new ArrayList<>());
            messageMap.put(name, list);
        }

        list.add(object);
    }

    public List<Object> getMessages(String name) {
        List<Object> list = messageMap.get(name);

        if (list != null) {
            messageMap.remove(name);
            return list;
        }

        return new ArrayList<>();
    }

    public <E extends Event> void addEvent(Class<E> klass, E event) {
        eventQueue.addEvent(klass, event);
    }
}
