package jag.game.type;

import jag.RSDoublyLinkedNode;

public interface RSDefinitionProperty extends RSDoublyLinkedNode {

    boolean isDeleteOnUse();

    int getDefaultInteger();

    String getDefaultString();

    char getType();

}