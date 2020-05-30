package jag.game.relationship;

import jag.RSProvider;

public interface RSChatHistory extends RSProvider {

    int getCaret();

    RSChatLine[] getBuffer();

}