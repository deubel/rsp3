package jag.game.option;

import jag.RSProvider;

import java.util.LinkedHashMap;

public interface RSClientPreferences extends RSProvider {

    boolean isLoginScreenAudioDisabled();

    int getResizable();

    boolean isRoofsHidden();

    boolean isRememberMe();

    LinkedHashMap getProperties();

}