package org.rspeer.game.position;

import org.rspeer.game.scene.Scene;

import static org.rspeer.game.position.AbsolutePosition.SCALE;

public class RelativePosition extends Position {

    public RelativePosition(int x, int y, int level) {
        super(x, y, level);
    }

    @Override
    public Position toWorld() {
        Position base = Scene.getBase();
        return new RelativePosition(x + base.x, y + base.y, level);
    }

    @Override
    public Position toScene() {
        return this;
    }

    @Override
    public Position toPrecise() {
        return new Position(x * SCALE, y * SCALE, level);
    }
}
