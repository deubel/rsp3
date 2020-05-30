package org.rspeer.commons.math;

import org.rspeer.game.adapter.type.SceneNode;
import org.rspeer.game.position.Position;

/**
 * Enum containing various {@link DistanceEvaluator}'s
 */
public enum Distance implements DistanceEvaluator {

    EUCLIDEAN {
        @Override
        public double evaluate(int x1, int y1, int x2, int y2) {
            return Math.hypot(x2 - x1, y2 - y1);
        }
    },

    EUCLIDEAN_SQUARED {
        @Override
        public double evaluate(int x1, int y1, int x2, int y2) {
            return Math.sqrt(Math.pow(x2 - x1, 2)) + Math.sqrt(Math.pow(y2 - y1, 2));
        }
    },

    MANHATTAN {
        @Override
        public double evaluate(int x1, int y1, int x2, int y2) {
            return Math.abs(x2 - x1) + Math.abs(y2 - y1);
        }
    },

    CHEBYSHEV {
        @Override
        public double evaluate(int x1, int y1, int x2, int y2) {
            return Math.max(Math.abs(x2 - x1), Math.abs(y2 - y1));
        }
    };

    private static final DistanceEvaluator DEFAULT_EVALUATOR = Distance.EUCLIDEAN_SQUARED;

    public static DistanceEvaluator getDefaultEvaluator() {
        return DEFAULT_EVALUATOR;
    }

    public static double between(SceneNode from, SceneNode to) {
        if (from == null || to == null) {
            return Double.MAX_VALUE;
        }
        return evaluate(DEFAULT_EVALUATOR, from, to);
    }

    public static double evaluate(DistanceEvaluator algo, SceneNode from, SceneNode to) {
        Position src = from.getPosition();
        Position dst = to.getPosition();
        return algo.evaluate(src.getX(), src.getY(), dst.getX(), dst.getY());
    }

    public abstract double evaluate(int x1, int y1, int x2, int y2);
}
