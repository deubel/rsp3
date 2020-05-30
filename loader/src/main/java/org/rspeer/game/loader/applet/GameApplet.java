package org.rspeer.game.loader.applet;

import org.rspeer.game.loader.config.GameConfig;

import java.applet.Applet;
import java.awt.*;
import java.util.function.Consumer;

@SuppressWarnings("deprecation")
public class GameApplet {

    private final GameConfig config;

    public GameApplet(GameConfig config) {
        this.config = config;
    }

    public Applet start(ClassLoader classLoader) {
        return start(classLoader, null);
    }

    public Applet start(ClassLoader classLoader, Consumer<Applet> callback) {
        try {
            String main = config.getEntryClass();
            Applet applet = (Applet) classLoader.loadClass(main).newInstance();
            applet.setBackground(Color.BLACK);
            applet.setSize(config.getAppletSize());
            applet.setLayout(null);
            applet.setStub(new GameAppletStub(config));
            applet.setVisible(true);
            applet.init();
            if (callback != null) {
                callback.accept(applet);
            }

            applet.start();
            return applet;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
