package jag.game.scene.entity;

public interface RSEntityMarker extends RSSceneObject {

    int getOrientation();

    int getSceneY();

    int getSceneX();

    int getMaxSceneY();

    int getMaxSceneX();

    int getFloorLevel();

    int getConfig();

    int getCenterFineX();

    int getCenterFineY();

    int getHeight();
}