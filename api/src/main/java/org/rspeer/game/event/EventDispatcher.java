package org.rspeer.game.event;

import org.rspeer.game.event.listener.EventListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventDispatcher {

    private final List<EventListener> listeners = new CopyOnWriteArrayList<>();

    public void subscribe(EventListener el) {
        listeners.add(el);
    }

    public void unsubscribe(EventListener el) {
        listeners.remove(el);
    }

    public void dispatch(Event e) {
        for (EventListener listener : listeners) {
            e.dispatch(listener);
        }
    }
}
