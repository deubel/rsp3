package org.rspeer.game.query.results;

import org.rspeer.game.adapter.type.SceneNode;
import org.rspeer.commons.math.Distance;
import org.rspeer.commons.math.DistanceEvaluator;
import org.rspeer.game.scene.Players;

import java.util.Collection;
import java.util.Comparator;

public class SceneNodeQueryResults<T extends SceneNode> extends QueryResults<T, SceneNodeQueryResults<T>> {

    public SceneNodeQueryResults(Collection<T> results) {
        super(results);
    }

    public final SceneNodeQueryResults<T> sortByDistanceFrom(DistanceEvaluator eval, SceneNode src) {
        return sort(Comparator.comparingDouble(value -> eval.evaluate(src, value)));
    }

    public final SceneNodeQueryResults<T> sortByDistanceFrom(SceneNode src) {
        return sortByDistanceFrom(Distance.getDefaultEvaluator(), src);
    }

    public final SceneNodeQueryResults<T> sortByDistance(DistanceEvaluator eval) {
        return sortByDistanceFrom(eval, Players.getLocal());
    }

    public final SceneNodeQueryResults<T> sortByDistance() {
        return sortByDistanceFrom(Players.getLocal());
    }

    public final T nearest() {
        return sortByDistance().first();
    }

    public final T furthest() {
        return sortByDistance().last();
    }

    public final T nearest(DistanceEvaluator evaluator) {
        return sortByDistance(evaluator).first();
    }

    public final T furthest(DistanceEvaluator evaluator) {
        return sortByDistance(evaluator).last();
    }

    public final T nearestTo(SceneNode entity) {
        return sortByDistanceFrom(entity).first();
    }

    public final T furthestFrom(SceneNode entity) {
        return sortByDistanceFrom(entity).last();
    }

    public final T nearestTo(DistanceEvaluator evaluator, SceneNode entity) {
        return sortByDistanceFrom(evaluator, entity).first();
    }

    public final T furthestFrom(DistanceEvaluator evaluator, SceneNode entity) {
        return sortByDistanceFrom(evaluator, entity).last();
    }

    @Override
    public SceneNodeQueryResults<T> self() {
        return this;
    }
}