package jag.game.type;

import org.rspeer.game.Vars;
import jag.RSDoublyLinkedNode;

public interface RSVarpbit extends RSDoublyLinkedNode {

    int getVarpIndex();

    int getLower();

    int getUpper();

    default int getBitCount() {
        return getUpper() - getLower();
    }

    default int getMask() {
        return Vars.BIT_MASKS[getBitCount()];
    }

    default int getValue() {
        int varpValue = Vars.get(getVarpIndex());
        return getValue(varpValue);
    }

    default int getValue(int varpValue) {
        int mask = Vars.BIT_MASKS[getUpper() - getLower()];
        return varpValue >> getLower() & mask;
    }
}