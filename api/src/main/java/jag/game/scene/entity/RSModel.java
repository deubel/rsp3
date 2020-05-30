package jag.game.scene.entity;

public interface RSModel extends RSEntity {

    int[] getZVertices();

    int[] getYTriangles();

    boolean isUseAABBBoundingBoxes();

    boolean isAabbEnabled();

    int[] getXTriangles();

    int[] getXVertices();

    int[] getZTriangles();

    int[] getYVertices();

}