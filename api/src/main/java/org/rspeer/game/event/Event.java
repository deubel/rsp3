package org.rspeer.game.event;

import org.rspeer.game.event.listener.EventListener;

import java.util.EventObject;

public abstract class Event<T> extends EventObject {

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public Event(T source) {
        super(source);
    }

    @Override
    public T getSource() {
        return (T) super.getSource();
    }

    public abstract void dispatch(EventListener listener);
}
