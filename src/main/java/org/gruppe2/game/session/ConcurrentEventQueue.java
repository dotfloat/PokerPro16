package org.gruppe2.game.session;

import org.gruppe2.game.event.Event;
import org.gruppe2.game.event.EventHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 */
class ConcurrentEventQueue {
    private final ConcurrentLinkedQueue<Event> eventQueue = new ConcurrentLinkedQueue<>();

    /* Adding handlers only happens on a single thread, so we don't care about thread safety here */
    private final Map<Class<?>, List<EventHandler<Event>>> handlerMap = new HashMap<>();

    /**
     * Register a new EventHandler to handle certain events
     *
     * @param klass The Class object of a class that implements Event. ex: PlayerJoinEvent.class
     * @param handler handler which will be called when the event occurs
     */
    void registerHandler(Class<Event> klass, EventHandler<Event> handler) {
        List<EventHandler<Event>> list = handlerMap.get(klass);

        if (list == null) {
            list = new ArrayList<>();
            handlerMap.put(klass, list);
        }

        list.add(handler);
    }

    /**
     * Add an event to be processed later
     * @param event event to be added
     */
    void addEvent(Event event) {
        eventQueue.add(event);
    }

    /**
     * Iterate over new events and send them out to their respective handlers
     */
    void process() {
        Event event;

        while ((event = eventQueue.poll()) != null) {
            List<EventHandler<Event>> list = handlerMap.get(event.getClass());

            if (list == null)
                continue;

            for (EventHandler<Event> handler : list)
                handler.handle(event);
        }
    }

    /**
     * @return number of pending events
     */
    int size() {
        return eventQueue.size();
    }
}