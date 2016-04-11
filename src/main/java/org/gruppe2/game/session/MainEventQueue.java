package org.gruppe2.game.session;

import org.gruppe2.game.event.Event;

import java.util.ArrayList;
import java.util.List;

class MainEventQueue {
    private List<ConcurrentEventQueue> queueList = new ArrayList<>();

    void registerQueue(ConcurrentEventQueue queue) {
        queueList.add(queue);
    }

    <T extends Event> void addEvent(Class<T> klass, T event) {
        for (ConcurrentEventQueue queue : queueList)
            queue.addEvent(klass, event);
    }

    void process() {
        for (ConcurrentEventQueue queue : queueList)
            queue.process();
    }
}
