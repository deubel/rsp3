package org.rspeer.game.adapter.type;

public interface Nameable {

    String getName();

    interface Query<Q extends Query<Q>> {

        Q names(String... names);

        Q nameContains(String... names);
    }
}
