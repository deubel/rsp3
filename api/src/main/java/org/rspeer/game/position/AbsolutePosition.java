package org.rspeer.game.position;

import org.rspeer.game.scene.Scene;

public class AbsolutePosition extends Position {

    public static final int SCALE = 128;

    public AbsolutePosition(int x, int y, int level) {
        super(x, y, level);
    }

    @Override
    public Position toWorld() {
        Position base = Scene.getBase();
        return new RelativePosition(x / SCALE + base.x, y / SCALE + base.y, level);
    }

    @Override
    public Position toScene() {
        return new RelativePosition(x / SCALE, y / SCALE, level);
    }

    @Override
    public Position toPrecise() {
        return this;
    }
}
