package jag.game.scene;

import jag.RSProvider;

public interface RSCollisionMap extends RSProvider {

    int[][] getFlags();

    int getWidth();

    int getInsetX();

    int getInsetY();

    int getHeight();

}