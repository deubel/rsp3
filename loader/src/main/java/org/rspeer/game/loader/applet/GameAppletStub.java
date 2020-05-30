package org.rspeer.game.loader.applet;

import org.rspeer.game.loader.config.GameConfig;

import java.applet.AppletContext;
import java.applet.AppletStub;
import java.net.MalformedURLException;
import java.net.URL;

@SuppressWarnings("deprecation")
public class GameAppletStub implements AppletStub {

    private final GameConfig config;

    public GameAppletStub(GameConfig config) {
        this.config = config;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public URL getDocumentBase() {
        return getCodeBase();
    }

    @Override
    public URL getCodeBase() {
        try {
            return new URL(config.getCodeBase());
        } catch (MalformedURLException ex) {
            return null;
        }
    }

    @Override
    public String getParameter(String name) {
        return config.getParameter(name);
    }

    @Override
    public AppletContext getAppletContext() {
        return null;
    }

    @Override
    public void appletResize(int width, int height) {

    }
}
