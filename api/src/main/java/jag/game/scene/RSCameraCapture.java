package jag.game.scene;

import jag.RSProvider;

public interface RSCameraCapture extends RSProvider {

    int getMinFineY();

    int getFlag();

    int getMinFineX();

    int getComponentHeight();

    int getMaxFineY();

    int getMaxFineX();

    int getTileHeight();

}