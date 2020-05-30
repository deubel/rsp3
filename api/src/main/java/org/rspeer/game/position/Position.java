package org.rspeer.game.position;

import org.rspeer.game.adapter.type.SceneNode;
import org.rspeer.game.scene.Scene;

//TODO DynamicPosition
public class Position implements SceneNode {

    protected final int x, y;
    protected final int level;

    public Position(int x, int y, int level) {
        this.x = x;
        this.y = y;
        this.level = level;
    }

    public Position(int x, int y) {
        this(x, y, 0);
    }

    public static Position fromRelative(int x, int y, int level) {
        Position base = Scene.getBase();
        return new Position(base.x + x, base.y + y, level);
    }

    public static Position fromAbsolute(int x, int y, int level) {
        Position base = Scene.getBase();
        return new Position(base.x + (x / AbsolutePosition.SCALE), base.y + (y / AbsolutePosition.SCALE), level);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getFloorLevel() {
        return level;
    }

    public Position translate(int x, int y) {
        return new Position(this.x + x, this.y + y, level);
    }

    public Position toWorld() {
        return this;
    }

    public Position toScene() {
        Position base = Scene.getBase();
        return new RelativePosition(x - base.x, y - base.y, level);
    }

    public Position toPrecise() {
        Position base = Scene.getBase();
        return new AbsolutePosition((x - base.x) * AbsolutePosition.SCALE, (y - base.y) * AbsolutePosition.SCALE, level);
    }

    @Override
    public Position getPosition() {
        return this;
    }

    public String toString() {
        return "Position[x=" + this.x + ", y=" + this.y + ", level=" + this.level + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position) {
            Position other = (Position) obj;
            return other.x == x && other.y == y && other.level == level;
        }
        return false;
    }

    public boolean isInScene() {
        Position scene = toScene();
        return scene.x >= 0 && scene.x < 104
                && scene.y >= 0 && scene.y < 104
                && scene.level >= 0 && scene.level < 4;
    }

    @Override
    public int hashCode() {
        return level << 28 | x << 14 | y;
    }
}
