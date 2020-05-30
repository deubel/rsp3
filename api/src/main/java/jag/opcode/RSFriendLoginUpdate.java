package jag.opcode;

import jag.RSProvider;
import jag.game.relationship.RSNamePair;

public interface RSFriendLoginUpdate extends RSProvider {

    short getWorld();

    RSNamePair getNamePair();

    int getTime();

}