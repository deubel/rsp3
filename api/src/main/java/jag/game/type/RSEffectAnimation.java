package jag.game.type;

import jag.RSDoublyLinkedNode;

public interface RSEffectAnimation extends RSDoublyLinkedNode {

    int getOrientation();

    int getModelId();

    int getAmbience();

    int getContrast();

    int getScaleXY();

    int getScaleZ();

    int getAnimation();

}