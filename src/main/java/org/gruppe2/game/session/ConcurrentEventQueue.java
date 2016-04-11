package org.gruppe2.game.session;

import org.gruppe2.game.event.Event;
import org.gruppe2.game.event.EventHandler;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 */
class ConcurrentEventQueue {
    private Map<Class<?>, List<EventHandler<Event>>> handlerMap = new HashMap<>();
    private ConcurrentLinkedQueue<Map.Entry<Class<?>, Event>> queue = new ConcurrentLinkedQueue<>();

    /**
     * Register a new EventHandler for to handle certain events
     * @param klass The Class object of the event class. ex: PlayerJoinEvent.class
     * @param handler
     * @param <T>
     */
    <T extends Event> void registerHandler(Class<T> klass, EventHandler<T> handler) {
        List<EventHandler<Event>> list = handlerMap.get(klass);

        if (list == null) {
            list = new ArrayList<>();
            handlerMap.put(klass, list);
        }

        //noinspection unchecked
        list.add((EventHandler<Event>) handler);
    }

    /**
     * Add a new event to the queue
     *
     * This method should be thread safe
     *
     * @param klass The Class object of the event class. ex: PlayerJoinEvent.class
     * @param event An event object
     */
    <T extends Event> void addEvent(Class<T> klass, T event) {
        queue.add(new AbstractMap.SimpleEntry<>(klass, event));
    }

    /**
     * Iterate over new events and send them out to their respective handlers
     */
    void process() {
        Map.Entry<Class<?>, Event> entry;

        while ((entry = queue.poll()) != null) {
            List<EventHandler<Event>> handlerList = handlerMap.get(entry.getKey());

            if (handlerList == null)
                continue;

            for (EventHandler<Event> handler : handlerList)
                handler.handle(entry.getValue());
        }
    }

    int numPending() {
        return queue.size();
    }
}
