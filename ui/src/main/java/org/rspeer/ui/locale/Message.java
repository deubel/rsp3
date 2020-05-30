package org.rspeer.ui.locale;

import org.rspeer.commons.Pair;
import org.rspeer.environment.preferences.BotPreferences;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum Message {

    SCRIPT_SELECTOR("Script Selector"),
    INTERFACE_EXPLORER("Interface Explorer"),
    RELOAD("Reload"),
    STOP("Stop"),
    FILE("File"),
    EXIT("Exit"),
    SCREENSHOT("Screenshot"),
    ABOUT("About"),
    ADD("Add"),
    REMOVE("Remove"),
    VIEW("View"),
    START("Start"),
    PAUSE("Pause"),
    RESUME("Resume"),
    SAVE("Save"),
    EDIT("Edit"),
    HELP("Help"),
    WINDOW("Window"),
    ALWAYS_ON_TOP("Always on top"),
    DEBUG("Debug");

    //certain locales dont exist in the jdk
    public static final Locale LOCALE_DUTCH = new Locale("nl", "NL");

    private final String english;
    private final Map<Locale, String> entries;

    Message(String english, Pair<Locale, String>... entries) {
        this.english = english;
        this.entries = new HashMap<>();

        for (Pair<Locale, String> entry : entries) {
            this.entries.put(entry.getLeft(), entry.getRight());
        }
    }

    public String getActive(BotPreferences prefs) {
        return get(prefs.getLocale());
    }

    public String get(Locale locale) {
        return entries.getOrDefault(locale, english);
    }
}
