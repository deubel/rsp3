package org.rspeer.game.adapter.type;

import org.rspeer.commons.math.Distance;
import org.rspeer.commons.math.DistanceEvaluator;
import org.rspeer.game.position.Position;
import org.rspeer.game.scene.Players;

public interface SceneNode {

    default int getX() {
        return getPosition().getX();
    }

    default int getY() {
        return getPosition().getY();
    }

    Position getPosition();

    default double distance(SceneNode other) {
        return Distance.between(this, other);
    }

    default double distance() {
        return distance(Players.getLocal());
    }

    default double distance(DistanceEvaluator evaluator, SceneNode other) {
        return evaluator.evaluate(this, other);
    }

    default double distance(DistanceEvaluator evaluator) {
        return distance(evaluator, Players.getLocal());
    }

    default int getFloorLevel() {
        return getPosition().getFloorLevel();
    }

    interface Query<Q extends Query<Q>> {

        Q on(Position... positions);

        Q within(SceneNode src, int distance);

        Q within(int distance);

        Q distanceEvaluator(DistanceEvaluator distanceEvaluator);
    }
}
