package org.rspeer.game.loader;

import org.rspeer.commons.Configuration;
import org.rspeer.game.loader.applet.GameApplet;
import org.rspeer.game.loader.config.GameConfig;
import org.rspeer.game.loader.gamepack.GamePack;
import jag.game.RSClient;
import org.rspeer.injector.Injector;
import org.rspeer.injector.script.Modscript;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * Utility class providing functions to load and inject the game
 */
public class GameLoader {

    public static void load(boolean local, Consumer<RSClient> later) throws IOException {
        RSClient result = null;
        if (local) {
            result = fromLocalModscript();
        }

        later.accept(result);
    }

    private static RSClient fromLocalModscript() throws IOException {
        GameConfig config = GameConfig.fetch();

        GamePack gamePack = new GamePack(config);
        gamePack.downloadIfOutdated();

        Injector injector = new Injector(Modscript.load(Configuration.Paths.MODSCRIPT_LOCATION));
        ClassLoader classLoader = gamePack.getInjectedLoader(injector);
        GameApplet appletLoader = new GameApplet(config);
        return (RSClient) appletLoader.start(classLoader);
    }
}
