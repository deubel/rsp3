package jag.game;

import jag.RSLinkedList;
import jag.RSNode;
import jag.game.type.RSHealthBarDefinition;

public interface RSHealthBar extends RSNode {

    RSLinkedList<RSHitUpdate> getHitsplats();

    RSHealthBarDefinition getDefinition();

}