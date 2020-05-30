package org.rspeer.commons.math;

import org.rspeer.game.adapter.type.SceneNode;
import org.rspeer.game.position.Position;

/**
 * Interface for distance calculations
 */
public interface DistanceEvaluator {

    double evaluate(int x1, int y1, int x2, int y2);

    default double evaluate(SceneNode from, SceneNode to) {
        Position w1 = from.getPosition();
        Position w2 = to.getPosition();
        return this.evaluate(w1.getX(), w1.getY(), w2.getX(), w2.getY());
    }
}

