package jag.game.scene.entity;

public interface RSBoundaryDecor extends RSSceneObject {

    int getAbsoluteX();

    int getOffsetX();

    int getAbsoluteY();

    int getOrientation();

    int getOffsetY();

    int getRenderConfig();

    RSEntity getLinkedEntity();

    int getConfig();

    int getHeight();
}