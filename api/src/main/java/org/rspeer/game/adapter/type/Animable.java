package org.rspeer.game.adapter.type;

import org.rspeer.game.effect.Animation;

public interface Animable {

    Animation getAnimation();

    default int getAnimationId() {
        Animation animation = getAnimation();
        return animation != null ? animation.getId() : -1;
    }

    default boolean isAnimating() {
        return getAnimation() != null;
    }

    interface Query<Q extends Query<Q>> {

        Q animations(int... animations);

        Q animating();

        Q inanimate();
    }
}
