package jag.game.relationship;

import jag.RSDoublyLinkedNode;

public interface RSChatLine extends RSDoublyLinkedNode {

    String getSource();

    int getType();

    String getMessage();

}