package org.rspeer.game.social;

import org.rspeer.commons.StringCommons;
import org.rspeer.game.Game;
import org.rspeer.game.adapter.social.Associate;
import org.rspeer.game.adapter.social.Chatter;
import org.rspeer.game.query.social.AssociateQuery;
import org.rspeer.game.query.social.RelationshipQuery;
import jag.game.relationship.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Relationships {

    private static RSRelationshipSystem getSystem() {
        return Game.getClient().getRelationshipSystem();
    }

    public static RelationshipQuery<RSIgnoredPlayer, Chatter<RSIgnoredPlayer>> ignores() {
        RSRelationshipSystem system = getSystem();
        if (system == null) {
            return new RelationshipQuery<>(Collections::emptyList);
        }

        RSIgnoreListContext container = system.getIgnoreListContext();

        if (container == null) {
            return new RelationshipQuery<>(Collections::emptyList);
        }

        RSIgnoredPlayer[] raw = container.getChatters();
        if (raw == null) {
            return new RelationshipQuery<>(Collections::emptyList);
        }

        List<Chatter<RSIgnoredPlayer>> chatters = new ArrayList<>();
        for (int i = 0; i < container.getCount(); i++) {
            RSIgnoredPlayer chatter = raw[i];
            if (chatter != null) {
                chatters.add(new Chatter<>(chatter));
            }
        }
        return new RelationshipQuery<>(() -> chatters);
    }

    public static AssociateQuery<RSBefriendedPlayer, Associate<RSBefriendedPlayer>> friends() {
        RSRelationshipSystem system = getSystem();
        if (system == null) {
            return new AssociateQuery<>(Collections::emptyList);
        }

        RSFriendListContext container = system.getFriendListContext();

        if (container == null) {
            return new AssociateQuery<>(Collections::emptyList);
        }

        RSBefriendedPlayer[] raw = container.getChatters();
        if (raw == null) {
            return new AssociateQuery<>(Collections::emptyList);
        }

        List<Associate<RSBefriendedPlayer>> chatters = new ArrayList<>();
        for (int i = 0; i < container.getCount(); i++) {
            RSBefriendedPlayer chatter = raw[i];
            if (chatter != null) {
                chatters.add(new Associate<>(chatter));
            }
        }
        return new AssociateQuery<>(() -> chatters);
    }

    public static boolean add(Context context, String name) {
        RSRelationshipSystem system = getSystem();
        if (system == null) {
            return false;
        }

        switch (context) {
            case FRIENDS_LIST:
                system.befriend(name);
                return true;
            case IGNORE_LIST:
                system.ignore(name);
                return true;
            default: {
                return false;
            }
        }
    }

    public static boolean remove(Context context, String name) {
        RSRelationshipSystem system = getSystem();
        if (system == null) {
            return false;
        }

        switch (context) {
            case FRIENDS_LIST:
                system.unbefriend(name);
                return true;
            case IGNORE_LIST:
                system.unignore(name);
                return true;
            default: {
                return false;
            }
        }
    }

    public enum Context {
        FRIENDS_LIST,
        IGNORE_LIST;
    }

    public static class Clan {

        public static AssociateQuery<RSClanMember, Associate<RSClanMember>> members() {
            RSClanSystem container = Game.getClient().getClanSystem();
            if (container == null) {
                return new AssociateQuery<>(Collections::emptyList);
            }

            RSClanMember[] raw = container.getChatters();
            if (raw == null) {
                return new AssociateQuery<>(Collections::emptyList);
            }

            List<Associate<RSClanMember>> chatters = new ArrayList<>();
            for (int i = 0; i < container.getCount(); i++) {
                RSClanMember chatter = raw[i];
                if (chatter != null) {
                    chatters.add(new Associate<>(chatter));
                }
            }
            return new AssociateQuery<>(() -> chatters);
        }

        public static String getName() {
            RSClanSystem container = Game.getClient().getClanSystem();
            if (container == null) {
                return "";
            }
            return StringCommons.sanitize(container.getChannelName());
        }

        public static String getOwner() {
            RSClanSystem container = Game.getClient().getClanSystem();
            if (container == null) {
                return "";
            }
            return StringCommons.sanitize(container.getChannelOwner());
        }
    }
}
