package jag.game.scene;

import jag.RSProvider;

public interface RSTileModel extends RSProvider {

    boolean isFlatShade();

    int getShape();

    int getOverlay();

    int getRotation();

    int getUnderlay();

}