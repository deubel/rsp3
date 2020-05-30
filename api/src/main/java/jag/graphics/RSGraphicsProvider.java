package jag.graphics;

import jag.RSProvider;

import java.awt.*;

public interface RSGraphicsProvider extends RSProvider {

    Image getImage();

    void drawGame(Graphics arg0, int arg1, int arg2);

}