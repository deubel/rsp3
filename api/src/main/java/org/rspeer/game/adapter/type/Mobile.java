package org.rspeer.game.adapter.type;

public interface Mobile {

    boolean isMoving();

    interface Query<Q extends Query<Q>> {

        Q moving();

        Q stationary();
    }
}
