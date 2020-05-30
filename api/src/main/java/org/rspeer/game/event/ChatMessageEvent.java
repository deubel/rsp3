package org.rspeer.game.event;

import org.rspeer.commons.StringCommons;
import org.rspeer.game.event.listener.ChatMessageListener;
import org.rspeer.game.event.listener.EventListener;

public class ChatMessageEvent extends Event<String> {

    private final int type;
    private final String contents;
    private final String channel;

    public ChatMessageEvent(int type, String source, String contents, String channel) {
        super(StringCommons.sanitize(source));
        this.type = type;
        this.contents = StringCommons.sanitize(contents);
        this.channel = channel != null ? StringCommons.sanitize(channel) : null;
    }

    @Override
    public void dispatch(EventListener listener) {
        if (listener instanceof ChatMessageListener) {
            ((ChatMessageListener) listener).notify(this);
        }
    }

    //TODO maybe enum or constants
    public int getType() {
        return type;
    }

    public String getContents() {
        return contents;
    }

    public String getChannel() {
        return channel;
    }
}
