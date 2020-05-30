package org.rspeer.game.event.listener;

import org.rspeer.game.event.ChatMessageEvent;

public interface ChatMessageListener extends EventListener {
    void notify(ChatMessageEvent e);
}
