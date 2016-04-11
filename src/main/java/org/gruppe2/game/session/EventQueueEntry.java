package org.gruppe2.game.session;

import org.gruppe2.game.event.Event;
import org.gruppe2.game.model.Model;

public class EventQueueEntry {
    private final Class<? extends Event> klass;
    private final Model model;
    private final Event event;

    public EventQueueEntry(Class<? extends Event> klass, Model model, Event event) {
        this.klass = klass;
        this.model = model;
        this.event = event;
    }

    public Class<? extends Event> getEventClass() {
        return klass;
    }

    public Model getModel() {
        return model;
    }

    public Event getEvent() {
        return event;
    }

    @Override
    public boolean equals(Object o) {
        return klass.equals(o);

    }

    @Override
    public int hashCode() {
        return klass.hashCode();
    }
}
