package org.gruppe2.game.session;

import org.gruppe2.game.event.Event;
import org.gruppe2.game.event.EventHandler;
import org.gruppe2.game.model.Model;

import java.util.*;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleEntry;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 */
public class ConcurrentEventQueue {
    private Map<Class<?>, List<EventMapEntry>> handlerMap = Collections.synchronizedMap(new HashMap<>());
    private ConcurrentLinkedQueue<EventQueueEntry> queue = new ConcurrentLinkedQueue<>();

    /**
     * Register a new EventHandler to handle certain events
     *
     * @param klass The Class object of the event class. ex: PlayerJoinEvent.class
     * @param handler handler which will be called when the event occurs
     * @param model model to handle the event for or null to handle for any model
     */
    public void registerHandler(Class<?> klass, Model model, EventHandler<Event> handler) {
        List<EventMapEntry> list = handlerMap.get(klass);

        if (list == null) {
            list = new ArrayList<>();
            handlerMap.put(klass, list);
        }

        list.add(new EventMapEntry(model, handler));
    }

    public void registerHandler(Class<?> klass, EventHandler<Event> handler) {
        registerHandler(klass, null, handler);
    }

    /**
     * Add a new event to the queue
     *
     * This method should be thread safe
     *
     * @param klass The Class object of the event class. ex: PlayerJoinEvent.class
     * @param event An event object
     */
    public void addEvent(Model model, Event event) {
        queue.add(new EventQueueEntry(event.getClass(), model, event));
    }

    public void addEvent(Event event) {
        addEvent(null, event);
    }

    /**
     * Iterate over new events and send them out to their respective handlers
     */
    public void process() {
        EventQueueEntry queueEntry;

        while ((queueEntry = queue.poll()) != null) {
            List<EventMapEntry> handlerList = handlerMap.get(queueEntry.getEventClass());

            if (handlerList == null)
                continue;

            for (EventMapEntry handlerEntry : handlerList) {
                if (handlerEntry.getModel() != null && !queueEntry.getModel().equals(handlerEntry.getModel()))
                    continue;

                handlerEntry.getHandler().handle(queueEntry.getEvent());
            }
        }
    }

    int numPending() {
        return queue.size();
    }
}