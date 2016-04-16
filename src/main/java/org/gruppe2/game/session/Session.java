package org.gruppe2.game.session;

import org.gruppe2.game.controller.Controller;
import org.gruppe2.game.event.Event;
import org.gruppe2.game.model.Model;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Session is PokerPro16's MVC framework for game logic. I couldn't come up with a better name, so whenever I use the
 * word 'Session', I mean this entire system.
 *
 * As an MVC framework, every piece of game logic can be split into the following components:
 * - Model for storing data
 * - View for using processed data
 * - Controller for processing data
 *
 * To communicate between controllers and views, Session uses events and messages.
 *
 * Events are sent by controllers, and can be captured by both controllers and views. An event can be sent to any number
 * of event handlers, and from any controller. Events are implemented as distinct immutable classes that implement the
 * {@link Event} interface. Each thread gets its own event queue that it can process at its own leisure.
 *
 * Messages are registered by controllers, and can be used by views and other controllers. Unlike events, only one
 * message handler can exist. (ie. only one controller can implement 'addPlayer'.) Messages are used as a way for views
 * to communicate with controllers.
 *
 * It is important to note that Session's controllers are designed to be self-sufficient. From the point of view of a
 * single controller, all other controllers are indistinguishable from views,and can't (well, shouldn't) be
 * accessed directly.
 *
 *
 * +--| Session Thread |--+       +--| Thread 1 |--+             M: Messages
 * |                      |       |                |             E: Events
 * |  +-| Controller |-+  |       |  +-| View |-+  |
 * |  |                |<-----M-----<|          |  |
 * |  |                |  |       |  |          |  |
 * |  |                |>-----E----->|          |  |
 * |  +----------------|  |       |  +----------+  |
 * |         ^  ^         |       |                |
 * |         |  |         |       +----------------+
 * |         M  E         |
 * |         |  |         |       +--| Thread 2 |--+
 * |         v  v         |       |                |
 * |  +-| Controller |-+  |       |  +-| View |-+  |
 * |  |                |<-----M-----<|          |  |
 * |  |                |  |       |  |          |  |
 * |  |                |>-----E----->|          |  |
 * |  +----------------+  |       |  +----------+  |
 * |                      |       |                |
 * +----------------------+       +----------------+
 *
 *
 * Every controller lives in the main Session thread, while views run in whichever thread they want. For this to work,
 * we give each thread their own {@link SessionContext} object, with, among other things, an event queue.
 *
 */
public abstract class Session implements Runnable {
    private final SessionEventQueue eventQueue = new SessionEventQueue();

    private final Map<Class<Model>, Model> modelMap = new ConcurrentHashMap<>();
    private final Map<Class<Controller>, Controller> controllerMap = new ConcurrentHashMap();
    private final Map<Method, List<Object[]>> messageMap = new ConcurrentHashMap();

    private final SessionContext context = new SessionContext(this);

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

        return this.getContext().createContext();
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

    SessionEventQueue getEventQueue() {
        return eventQueue;
    }

    public <M extends Model> void addModels(Class<M> klass) {
        modelMap.put(klass, Collections.synchronizedList(new ArrayList<>()));
    }

    public void addModel(Model model) {
        List<Model> list = new ArrayList<>();
        list.add(model);

        modelMap.put(model.getClass(), Collections.synchronizedList(list));
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

    public SessionContext getContext() {
        return context;
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
