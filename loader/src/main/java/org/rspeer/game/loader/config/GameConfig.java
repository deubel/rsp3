package org.rspeer.game.loader.config;

import org.rspeer.network.URLStreamConsumer;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Loads and stores config params required for loading and initializing the game
 */
public class GameConfig {

    private static final String CONFIG = "http://oldschool.runescape.com/jav_config.ws";
    private static final String GAME_HOME = "http://oldschool%d.runescape.com/";
    private static final String PARAM_IDENTIFIER = "param=";
    private final Map<String, String> params;

    private GameConfig(Map<String, String> params) {
        this.params = params;
    }

    public static GameConfig fetch() throws IOException {
        URLConnection hurl = new URL(CONFIG).openConnection();
        Map<String, String> parameters = new HashMap<>();
        URLStreamConsumer.consume(hurl, string -> true, string -> {
            String replaced = string.replace(PARAM_IDENTIFIER, "");
            int idx = replaced.indexOf('=');
            parameters.put(replaced.substring(0, idx), replaced.substring(idx + 1));
        });

        return new GameConfig(parameters);
    }

    public String getCodeBase() {
        return params.get("codebase");
    }

    public String getGamePack() {
        return getGamePack(2);
    }

    public String getGamePack(int world) {
        return String.format(GAME_HOME, world) + params.get("initial_jar");
    }

    public String getEntryClass() {
        return params.get("initial_class").replace(".class", "");
    }

    public Dimension getAppletSize() {
        try {
            return new Dimension(Integer.parseInt(params.get("applet_minwidth")), Integer.parseInt(params.get("applet_minheight")));
        } catch (NumberFormatException e) {
            return new Dimension(765, 503);
        }
    }

    public String getParameter(String key) {
        return params.get(key);
    }
}
