package org.rspeer.environment.preferences;

import java.util.Locale;

public class BotPreferences {

    private Locale locale = Locale.ENGLISH;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
