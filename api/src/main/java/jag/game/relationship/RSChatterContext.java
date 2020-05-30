package jag.game.relationship;

import jag.RSProvider;

import java.util.HashMap;

public interface RSChatterContext<T extends RSChatter> extends RSProvider {

    T[] getChatters();

    HashMap getDisplayNameCache();

    HashMap getPreviousNameCache();

    int getCount();

    int getCapacity();

}