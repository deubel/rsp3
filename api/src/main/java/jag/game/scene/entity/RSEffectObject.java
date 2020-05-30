package jag.game.scene.entity;

import jag.game.type.RSAnimationSequence;

public interface RSEffectObject extends RSEntity {

    int getAbsoluteX();

    RSAnimationSequence getSequence();

    int getAbsoluteY();

    int getFloorLevel();

    boolean isFinished();

    int getId();

    int getHeight();

}