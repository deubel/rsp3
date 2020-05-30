package org.rspeer.game.effect;

public class Hitsplat {

    private final int damage;
    private final int type;
    private final int cycle;

    public Hitsplat(int damage, int type, int cycle) {
        this.damage = damage;
        this.type = type;
        this.cycle = cycle;
    }

    public int getDamage() {
        return damage;
    }

    public int getType() {
        return type;
    }

    public int getCycle() {
        return cycle;
    }
}
