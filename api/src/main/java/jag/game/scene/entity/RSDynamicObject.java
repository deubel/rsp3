package jag.game.scene.entity;

import jag.game.type.RSAnimationSequence;

public interface RSDynamicObject extends RSEntity {

    RSAnimationSequence getSequence();

    int getSceneY();

    int getOrientation();

    int getSceneX();

    int getFloorLevel();

    int getId();

    int getType();

}