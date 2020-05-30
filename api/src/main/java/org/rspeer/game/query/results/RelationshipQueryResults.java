package org.rspeer.game.query.results;

import org.rspeer.game.adapter.social.Chatter;
import jag.game.relationship.RSChatter;

import java.util.Collection;
import java.util.Comparator;

public class RelationshipQueryResults<K extends RSChatter, T extends Chatter<K>>
        extends QueryResults<T, RelationshipQueryResults<K, T>> {

    public RelationshipQueryResults(Collection<T> results) {
        super(results);
    }

    @Override
    public RelationshipQueryResults<K, T> self() {
        return this;
    }

    public final RelationshipQueryResults<K, T> sortByName(boolean ascending) {
        Comparator<T> comparator = Comparator.comparing(Chatter::getName);
        return sort(ascending ? comparator : comparator.reversed());
    }

    public final RelationshipQueryResults<K, T> sortByPreviousName(boolean ascending) {
        Comparator<T> comparator = Comparator.comparing(Chatter::getPreviousName);
        return sort(ascending ? comparator : comparator.reversed());
    }
}
