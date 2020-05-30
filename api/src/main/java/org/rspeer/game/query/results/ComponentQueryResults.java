package org.rspeer.game.query.results;

import org.rspeer.game.adapter.component.InterfaceComponent;

import java.util.Collection;

public class ComponentQueryResults extends QueryResults<InterfaceComponent, ComponentQueryResults> {

    public ComponentQueryResults(Collection<InterfaceComponent> results) {
        super(results);
    }

    @Override
    public ComponentQueryResults self() {
        return this;
    }
}
