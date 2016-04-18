package org.gruppe2.game.session;

import org.gruppe2.game.event.Event;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 */
class SessionEventQueue {
    private final List<WeakReference<ConcurrentEventQueue>> queueList = Collections.synchronizedList(new ArrayList<>());

    void addQueue(ConcurrentEventQueue queue) {
        queueList.add(new WeakReference<>(queue));
    }

    void addEvent(Event event) {
        queueList
                .stream()
                .map(Reference::get)
                .filter(q -> q != null)
                .forEach(q -> q.addEvent(event));
    }

    void process() {
        for (int i = 0; i < queueList.size(); i++) {
            ConcurrentEventQueue queue = queueList.get(i).get();

            if (queue == null) {
                queueList.remove(i--);
                continue;
            }

            queue.process();
        }
    }
}
