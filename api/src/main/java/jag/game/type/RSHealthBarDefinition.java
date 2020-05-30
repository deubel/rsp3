package jag.game.type;

import jag.RSDoublyLinkedNode;

public interface RSHealthBarDefinition extends RSDoublyLinkedNode {

    int getOverlaySpriteId();

    int getUnderlaySpriteId();

    int getMaxWidth();

}