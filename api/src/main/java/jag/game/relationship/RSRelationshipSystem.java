package jag.game.relationship;

import jag.RSProvider;

public interface RSRelationshipSystem extends RSProvider {

    RSIgnoreListContext getIgnoreListContext();

    RSFriendListContext getFriendListContext();

    void befriend(String name);

    void unbefriend(String name);

    void ignore(String name);

    void unignore(String name);
}