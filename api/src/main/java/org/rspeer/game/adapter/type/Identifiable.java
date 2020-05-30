package org.rspeer.game.adapter.type;

public interface Identifiable {

    int getId();

    interface Query<Q extends Query<Q>> {
        Q ids(int... ids);
    }
}
