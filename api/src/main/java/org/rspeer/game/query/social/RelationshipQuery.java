package org.rspeer.game.query.social;

import org.rspeer.commons.ArrayUtils;
import org.rspeer.game.adapter.social.Chatter;
import org.rspeer.game.adapter.type.Nameable;
import org.rspeer.game.query.Query;
import org.rspeer.game.query.results.RelationshipQueryResults;
import jag.game.relationship.RSChatter;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class RelationshipQuery<K extends RSChatter, T extends Chatter<K>>
        extends Query<T, RelationshipQuery<K, T>, RelationshipQueryResults<K, T>>
        implements Nameable.Query<RelationshipQuery<K, T>> {

    private final Supplier<List<T>> provider;

    private String[] nameContains = null;
    private String[] names = null;
    private String[] aliases = null;

    public RelationshipQuery(Supplier<List<T>> provider) {
        this.provider = provider;
    }

    @Override
    public Supplier<List<T>> getDefaultProvider() {
        return provider;
    }

    @Override
    public RelationshipQuery<K, T> self() {
        return this;
    }

    @Override
    protected RelationshipQueryResults<K, T> createQueryResults(Collection<T> raw) {
        return new RelationshipQueryResults<>(raw);
    }

    @Override
    public RelationshipQuery<K, T> names(String... names) {
        this.names = names;
        return self();
    }

    @Override
    public RelationshipQuery<K, T> nameContains(String... names) {
        this.nameContains = names;
        return self();
    }

    public RelationshipQuery<K, T> aliases(String... aliases) {
        this.aliases = aliases;
        return self();
    }

    @Override
    public boolean test(T t) {
        if (names != null && !ArrayUtils.containsExactInsensitive(names, t.getName())) {
            return false;
        }

        if (nameContains != null && !ArrayUtils.containsPartialInsensitive(nameContains, t.getName())) {
            return false;
        }

        if (aliases != null && !ArrayUtils.contains(aliases, t.getPreviousName())) {
            return false;
        }

        return super.test(t);
    }
}
