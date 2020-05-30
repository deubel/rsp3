package jag.game.scene.entity;

import jag.RSNode;

public interface RSPendingSpawn extends RSNode {

    int getHitpoints();

    int getSceneY();

    int getOrientation();

    int getSceneX();

    int getDelay();

    int getFloorLevel();

    int getId();

    int getType();

}