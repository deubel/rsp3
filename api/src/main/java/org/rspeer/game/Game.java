package org.rspeer.game;

import org.rspeer.game.action.Action;
import org.rspeer.game.event.EventDispatcher;
import org.rspeer.game.provider.callback.EventMediator;
import jag.game.RSClient;
import jag.game.option.RSClientPreferences;

import java.awt.*;

public class Game {

    public static final int STATE_LOADING_GAME = 5;
    public static final int STATE_CREDENTIALS_SCREEN = 10;
    public static final int STATE_LOGGING_IN = 20;
    public static final int STATE_LOADING_REGION = 25;
    public static final int STATE_IN_GAME = 30;
    public static final int STATE_CONNECTION_LOST = 40;
    public static final int STATE_HOPPING_WORLD = 45;

    private static volatile RSClient client;

    public static RSClient getClient() {
        return client;
    }

    public static synchronized void setClient(RSClient client) {
        Game.client = client;
        client.setEventMediator(new EventMediator());
        client.setEventDispatcher(new EventDispatcher());
    }

    public static void queueAction(Action action) {
        getEventMediator().getEngineTasks().offer(action);
    }

    public static void fireScriptLater(int id, Object... args) {
        client.fireScriptLater(id, args);
    }

    public static RSClientPreferences getClientPreferences() {
        return client.getPreferences();
    }

    public static int getState() {
        return client.getGameState();
    }

    public static boolean isLoggedIn() {
        int state = getState();
        return state == STATE_IN_GAME || state == STATE_LOADING_REGION;
    }

    public static boolean isOnCredentialsScreen() {
        return getState() == STATE_CREDENTIALS_SCREEN;
    }

    public static boolean isLoadingRegion() {
        return getState() == STATE_LOADING_REGION;
    }

    public static int getEngineCycle() {
        return client.getEngineCycle();
    }

    public static Canvas getCanvas() {
        return client.getCanvas();
    }

    public static EventMediator getEventMediator() {
        return client.getEventMediator();
    }

    public static EventDispatcher getEventDispatcher() {
        return client.getEventDispatcher();
    }
}
