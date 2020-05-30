package org.rspeer.game.adapter.social;

import jag.game.relationship.RSAssociate;

public class Associate<T extends RSAssociate> extends Chatter<T> {

    public Associate(T provider) {
        super(provider);
    }

    public boolean isOnline() {
        T provider = getProvider();
        return provider != null && provider.getWorld() > 0;
    }

    public int getWorld() {
        T provider = getProvider();
        return provider != null ? provider.getWorld() : -1;
    }

    //TODO replace with enum ranks
    @Deprecated
    public int getRank() {
        T provider = getProvider();
        return provider != null ? provider.getRank() : -1;
    }
}
