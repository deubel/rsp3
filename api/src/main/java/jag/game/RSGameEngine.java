package jag.game;

import jag.RSProvider;

import java.applet.Applet;
import java.awt.*;

public interface RSGameEngine extends RSProvider {

    Canvas getCanvas();

    default Applet asApplet() {
        return (Applet) this;
    }
}