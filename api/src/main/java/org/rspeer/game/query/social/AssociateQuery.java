package org.rspeer.game.query.social;

import org.rspeer.commons.ArrayUtils;
import org.rspeer.game.adapter.social.Associate;
import org.rspeer.game.query.results.AssociateQueryResults;
import jag.game.relationship.RSAssociate;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class AssociateQuery<K extends RSAssociate, T extends Associate<K>> extends RelationshipQuery<K, T> {

    private int[] worlds = null;

    public AssociateQuery(Supplier<List<T>> provider) {
        super(provider);
    }

    @Override
    protected AssociateQueryResults<K, T> createQueryResults(Collection<T> raw) {
        return new AssociateQueryResults<>(raw);
    }

    @Override
    public AssociateQuery<K, T> self() {
        return this;
    }

    public AssociateQuery<K, T> worlds(int... worlds) {
        this.worlds = worlds;
        return self();
    }

    @Override
    public boolean test(T t) {
        if (worlds != null && !ArrayUtils.contains(worlds, t.getWorld())) {
            return false;
        }

        return super.test(t);
    }
}
