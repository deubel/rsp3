package org.rspeer.game.query.results;

import org.rspeer.game.component.Item;

import java.util.Collection;

public class ItemQueryResults extends QueryResults<Item, ItemQueryResults> {

    public ItemQueryResults(Collection<Item> results) {
        super(results);
    }

    @Override
    public ItemQueryResults self() {
        return this;
    }
}
