package jag.game.scene.entity;

public interface RSUnlitModel extends RSEntity {

    int[] getZVertices();

    byte getDefaultRenderPriority();

    int[] getXVertices();

    int[] getYVertices();

    RSModel light(int arg0, int arg1, int arg2, int arg3, int arg4);

}