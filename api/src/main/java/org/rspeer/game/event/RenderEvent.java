package org.rspeer.game.event;

import org.rspeer.game.event.listener.EventListener;
import org.rspeer.game.event.listener.RenderListener;

import java.awt.*;

public class RenderEvent extends Event<Graphics> {

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public RenderEvent(Graphics source) {
        super(source);
    }

    @Override
    public void dispatch(EventListener listener) {
        if (listener instanceof RenderListener) {
            ((RenderListener) listener).notify(this);
        }
    }
}
