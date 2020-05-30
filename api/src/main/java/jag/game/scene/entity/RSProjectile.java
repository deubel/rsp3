package jag.game.scene.entity;

import jag.game.type.RSAnimationSequence;

public interface RSProjectile extends RSEntity {

    double getSpeedZ();

    double getSpeedY();

    double getSpeedX();

    int getTargetIndex();

    int getFloorLevel();

    int getTargetDistance();

    int getSlope();

    double getHeightOffset();

    double getSpeed();

    double getAbsoluteX();

    RSAnimationSequence getSequence();

    double getAbsoluteY();

    int getXRotation();

    int getStartCycle();

    int getStartY();

    int getEndCycle();

    boolean isInMotion();

    int getId();

    int getStartX();

    int getYRotation();

    int getHeight();

}