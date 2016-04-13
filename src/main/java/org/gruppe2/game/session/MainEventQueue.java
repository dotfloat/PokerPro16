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

    void addEvent(Model model, Event event) {
        for (ConcurrentEventQueue queue : queueList)
            queue.addEvent(model, event);
    }

    void addEvent(Event event) {
        addEvent(null, event);
    }

    void process() {
        for (ConcurrentEventQueue queue : queueList)
            queue.process();
    }
}
