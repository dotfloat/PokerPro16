package org.gruppe2.game.session;

import org.gruppe2.game.controller.Controller;
import org.gruppe2.game.event.Event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Session is PokerPro16's MVC framework for game logic. I couldn't come up with a better name, so whenever I use the
 * word 'Session', I mean this entire system.
 * <p>
 * As an MVC framework, every piece of game logic can be split into the following components:
 * - Model for storing data
 * - View for using processed data
 * - Controller for processing data
 * <p>
 * To communicate between controllers and views, Session uses events and messages.
 * <p>
 * Events are sent by controllers, and can be captured by both controllers and views. An event can be sent to any number
 * of event handlers, and from any controller. Events are implemented as distinct immutable classes that implement the
 * {@link Event} interface. Each thread gets its own event queue that it can process at its own leisure.
 * <p>
 * Messages are registered by controllers, and can be used by views and other controllers. Unlike events, only one
 * message handler can exist. (ie. only one controller can implement 'addPlayer'.) Messages are used as a way for views
 * to communicate with controllers.
 * <p>
 * It is important to note that Session's controllers are designed to be self-sufficient. From the point of view of a
 * single controller, all other controllers are indistinguishable from views,and can't (well, shouldn't) be
 * accessed directly.
 * <p>
 * <p>
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
 * <p>
 * <p>
 * Every controller lives in the main Session thread, while views run in whichever thread they want. For this to work,
 * we give each thread their own {@link SessionContext} object, with, among other things, an event queue.
 */
public abstract class Session implements Runnable {
    private final SessionEventQueue eventQueue = new SessionEventQueue();
    private final SessionContext context = new SessionContext(this);

    private final List<Controller> controllerList = new ArrayList<>();

    private final Map<Class<?>, Object> modelMap = new ConcurrentHashMap<>();

    private final ConcurrentLinkedQueue<MessageEntry> messageQueue = new ConcurrentLinkedQueue<>();
    private final Map<String, MessageHandler> messageNameMap = new ConcurrentHashMap<>();

    private final List<TimerTask> taskList = new ArrayList<>();

    private volatile RunState state = RunState.STARTING;

    /**
     * JavaFX-style start method.
     *
     * @param klass
     * @return
     */
    public static SessionContext start(Class<? extends Session> klass, Object... args) {
        Session session;
        Class<?>[] argTypes;
        Thread thread;

        try {
            argTypes = (Class<?>[]) Arrays.stream(args).map(Object::getClass).toArray(Class[]::new);

            session = klass.getConstructor(argTypes).newInstance(args);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }

        thread = new Thread(session);
        thread.setName("Session");
        thread.start();

        return session.getContext().createContext();
    }

    @Override
    public void run() {
        init();

        controllerList.forEach(Controller::init);

        state = RunState.RUNNING;
        
        while (state != RunState.STOPPED) {
            context.getEventQueue().process();
            processMessageQueue();
            processTaskList();

            update();

            controllerList.forEach(Controller::update);

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public TimerTask setTask(long ms, Runnable runnable) {
        if (ms <= 0) {
            throw new IllegalArgumentException();
        }

        TimerTask task = new TimerTask(System.currentTimeMillis() + ms, runnable);

        taskList.add(task);

        return task;
    }

    public void cancelTask(TimerTask task) {
        taskList.remove(task);
    }

    private void processTaskList() {
        long curTime = System.currentTimeMillis();

        for (int i = 0; i < taskList.size(); i++) {
            TimerTask task = taskList.get(i);

            if (task.getExpireTimeMs() <= curTime) {
                task.run();
                taskList.remove(i--);
            }
        }
    }

	private void processMessageQueue() {
        MessageEntry entry;
        while ((entry = messageQueue.poll()) != null) {
            MessageHandler handler;
            boolean ret = false;

            handler = messageNameMap.get(entry.getName());

            try {
                ret = handler.call(entry.getArgs());
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

            entry.getReturnVal().set(ret);
        }
    }

    public abstract void init();

    public abstract void update();

    SessionEventQueue getEventQueue() {
        return eventQueue;
    }

    void addModel(Object model) {
        modelMap.put(model.getClass(), model);
    }

    void addController(Class<? extends Controller> klass) {
        if (isReady())
            throw new RuntimeException("Can't add controller after session has started");

        Controller controller;

        try {
            controller = klass.newInstance();
            controller.setSession(this);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return;
        }

        setAnnotatedMessages(controller);
        getContext().setAnnotated(controller);

        controllerList.add(controller);
    }

    Object getModel(Class<?> klass) {
        return modelMap.get(klass);
    }

    /**
     * Register a message method
     * @param method message method
     */
    void addMessage(String name, MessageHandler handler) {
        if (messageNameMap.containsKey(name))
            throw new RuntimeException(String.format("Message %s: already registered", name));

        messageNameMap.put(name, handler);
    }

    /**
     * Add all @Message-annotated methods to the list of messages
     * @param object An Object that might have methods with @Message
     */
    private void setAnnotatedMessages(Object object) {
        MessageHandler handler;

        for (Method m : object.getClass().getDeclaredMethods()) {
            if (m.getAnnotation(Message.class) == null)
                continue;

            if (m.getReturnType().equals(Boolean.TYPE)) {
                // If method returns a boolean, create a lambda that calls the method and returns whatever the method returns.
                handler = args -> {
                    verifyMessageArguments(m, args);
                    return (Boolean) m.invoke(object, args);
                };
            } else if (m.getReturnType().equals(Void.TYPE)) {
                // Otherwise, if the method returns a void, create a lambda that calls the method and returns true.
                handler = args -> {
                    verifyMessageArguments(m, args);
                    m.invoke(object, args);
                    return true;
                };
            } else {
                throw new RuntimeException(String.format("Message %s: return type must be either boolean or void",
                        m.getName()));
            }

            addMessage(m.getName(), handler);
        }
    }

    private static void verifyMessageArguments(Method method, Object... args) {
        if (method.getParameterCount() != args.length)
            throw new RuntimeException(String.format("Message %s: Expected %d parameters, got %d", method.getName(),
                    method.getParameterCount(), args.length));

        for (int i = 0; i < args.length; i++) {
            Class<?> expected = method.getParameterTypes()[i];
            Object arg = args[i];

            if (!expected.isInstance(arg)) {
                throw new RuntimeException(String.format("Message %s, parameter %d: Type expected %s, got %s",
                        method.getName(), i, expected, arg.getClass()));
            }
        }
    }

    /**
     * Send a message to a controller
     *
     * @param name the name of the message
     * @param args message arguments
     * @return a future of the return value
     */
    Query<Boolean> sendMessage(String name, Object... args) {
        MessageEntry entry = new MessageEntry(name, args);

        messageQueue.add(entry);

        return entry.getReturnVal();
    }

    /**
     * Add an event to the event queue
     * @param event event to be added
     */
    public void addEvent(Event event) {
        eventQueue.addEvent(event);
    }

    /**
     * Check if the session has started
     * @return true if started, false is still initialising
     */
    boolean isReady() {
        return state != RunState.STARTING;
    }

    /**
     * Get the SessionContext used by the Session object
     * @return Session's SessionContext
     */
    public SessionContext getContext() {
        return context;
    }

    private enum RunState {STARTING, RUNNING, QUITTING, STOPPED}
}