package org.gruppe2.game.session;

import org.gruppe2.game.event.Event;
import org.gruppe2.game.event.EventHandler;
import org.gruppe2.game.model.Model;

public class EventMapEntry {
    private final Model model;
    private final EventHandler<Event> handler;

    public EventMapEntry(Model model, EventHandler<Event> handler) {
        this.model = model;
        this.handler = handler;
    }

    public Model getModel() {
        return model;
    }

    public EventHandler<Event> getHandler() {
        return handler;
    }
}
