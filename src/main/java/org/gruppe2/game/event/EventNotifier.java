package org.gruppe2.game.event;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class EventNotifier {
    private Map<Class<? extends Event>, List<WeakReference>> handlers = new HashMap<>();

    public <T extends Event> void register(Class<T> klass, EventHandler<T> handler) {
        List<WeakReference> list = handlers.get(klass);
        WeakReference<EventHandler<? extends Event>> weakRef = new WeakReference<>(handler);

        if (list == null) {
            list = new ArrayList<>();
            handlers.put(klass, list);
        }

        list.add(weakRef);
    }

    <T extends Event> void notify(Class<T> klass, T event) {
        List<WeakReference> list = handlers.get(klass);

        if (list == null)
            return;

        for (int i = 0; i < list.size(); i++) {
            WeakReference weakRef = list.get(i);
            EventHandler handler = (EventHandler) weakRef.get();

            if (handler == null) {
                list.remove(i--);
                continue;
            }

            handler.handle(event);
        }
    }
}
