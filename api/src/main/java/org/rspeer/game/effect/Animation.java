package org.rspeer.game.effect;

public class Animation {

    private final int id;
    private final int frame;

    public Animation(int id, int frame) {
        this.id = id;
        this.frame = frame;
    }

    public int getId() {
        return id;
    }

    public int getFrame() {
        return frame;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[id=" + id + ",frame=" + frame + "]";
    }
}
