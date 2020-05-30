package jag.game.scene.entity;

import jag.RSProvider;

public interface RSPickableStack extends RSProvider {

    int getAbsoluteX();

    int getAbsoluteY();

    long getUid();

    RSEntity getMiddle();

    RSEntity getTop();

    RSEntity getBottom();

    int getHeight();

}