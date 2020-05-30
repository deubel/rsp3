package jag.game.type;

import jag.RSDoublyLinkedNode;

public interface RSAnimationSequence extends RSDoublyLinkedNode {

    boolean isStretch();

    int getMainHand();

    int getReplayMode();

    int getLoopOffset();

    int[] getFrameIds();

    int getPriority();

    int getAnimatingPrecedence();

    int getMaxLoops();

    int[] getFrameLengths();

    int getWalkingPrecedence();

    int[] getInterleaveOrder();

    int getOffHand();

}