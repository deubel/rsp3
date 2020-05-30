package jag.game.scene;

import jag.RSProvider;

public interface RSTilePaint extends RSProvider {

    boolean isFlatShade();

    int getTexture();

    int getRgb();

}