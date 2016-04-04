package org.gruppe2.game.event;

public interface EventHandler<T extends Event> {
    void handle(T event);
}
