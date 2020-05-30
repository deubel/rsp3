package jag.game.scene.entity;

public interface RSBoundary extends RSSceneObject {

    int getAbsoluteX();

    int getAbsoluteY();

    int getOrientation();

    RSEntity getLinkedEntity();

    int getLinkedOrientation();

    int getConfig();

    int getHeight();

}