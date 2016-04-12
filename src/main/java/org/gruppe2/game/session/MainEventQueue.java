package org.gruppe2.game.session;

import org.gruppe2.game.event.Event;
import org.gruppe2.game.model.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class MainEventQueue {
    private List<ConcurrentEventQueue> queueList = Collections.synchronizedList(new ArrayList<>());

    void registerQueue(ConcurrentEventQueue queue) {
        queueList.add(queue);
    }

    <E extends Event, M extends Model> void addEvent(Class<E> klass, M model, E event) {
        for (ConcurrentEventQueue queue : queueList)
            queue.addEvent(klass, model, event);
    }

    <E extends Event> void addEvent(Class<E> klass, E event) {
        addEvent(klass, null, event);
    }

    void process() {
        for (ConcurrentEventQueue queue : queueList)
            queue.process();
    }
}
