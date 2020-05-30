package org.rspeer.game.query.results;

import org.rspeer.game.adapter.social.Associate;
import jag.game.relationship.RSAssociate;

import java.util.Collection;
import java.util.Comparator;

public class AssociateQueryResults<K extends RSAssociate, T extends Associate<K>>
        extends RelationshipQueryResults<K, T> {

    public AssociateQueryResults(Collection<T> results) {
        super(results);
    }

    @Override
    public AssociateQueryResults<K, T> self() {
        return this;
    }

    public final AssociateQueryResults<K, T> sortByWorld(boolean ascending) {
        Comparator<T> comparator = Comparator.comparing(Associate::getWorld);
        return (AssociateQueryResults<K, T>) sort(ascending ? comparator : comparator.reversed());
    }
}
