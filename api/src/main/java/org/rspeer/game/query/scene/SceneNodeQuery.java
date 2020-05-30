package org.rspeer.game.query.scene;

import org.rspeer.commons.ArrayUtils;
import org.rspeer.commons.math.Distance;
import org.rspeer.commons.math.DistanceEvaluator;
import org.rspeer.game.adapter.type.SceneNode;
import org.rspeer.game.position.Position;
import org.rspeer.game.query.Query;
import org.rspeer.game.query.results.SceneNodeQueryResults;

public abstract class SceneNodeQuery<T extends SceneNode, Q extends SceneNodeQuery<T, Q>>
        extends Query<T, Q, SceneNodeQueryResults<T>>
        implements SceneNode.Query<SceneNodeQuery<T, Q>> {

    private DistanceEvaluator distanceEvaluator = Distance.getDefaultEvaluator();

    private Integer distanceFromDefined = null;
    private Integer distanceFromLocal = null;

    private SceneNode from = null;

    private Position[] positions = null;

    public Q on(Position... positions) {
        this.positions = positions;
        return self();
    }

    public Q within(SceneNode src, int distance) {
        from = src;
        this.distanceFromDefined = distance;
        return self();
    }

    public Q within(int distance) {
        distanceFromLocal = distance;
        return self();
    }

    public Q distanceEvaluator(DistanceEvaluator distanceEvaluator) {
        this.distanceEvaluator = distanceEvaluator;
        return self();
    }

    @Override
    public boolean test(T entity) {
        if (distanceFromLocal != null && entity.distance(distanceEvaluator) > distanceFromLocal) {
            return false;
        }

        if (distanceFromDefined != null && from != null && from.distance(distanceEvaluator, entity) > distanceFromDefined) {
            return false;
        }

        if (positions != null && !ArrayUtils.contains(positions, entity.getPosition())) {
            return false;
        }

        return super.test(entity);
    }
}
