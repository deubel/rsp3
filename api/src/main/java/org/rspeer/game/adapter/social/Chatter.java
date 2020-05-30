package org.rspeer.game.adapter.social;

import org.rspeer.commons.StringCommons;
import org.rspeer.game.adapter.Adapter;
import jag.game.relationship.RSChatter;
import jag.game.relationship.RSNamePair;

public class Chatter<T extends RSChatter> extends Adapter<T> {

    public Chatter(T provider) {
        super(provider);
    }

    public String getName() {
        T provider = getProvider();
        if (provider == null) {
            return "";
        }

        return nameOf(provider.getDisplayName());
    }

    public String getPreviousName() {
        T provider = getProvider();
        if (provider == null) {
            return "";
        }

        return nameOf(provider.getPreviousName());
    }

    private String nameOf(RSNamePair pair) {
        if (pair == null) {
            return "";
        }

        String name = pair.getRaw();
        return name != null ? StringCommons.replaceJagspace(name) : "";
    }
}
