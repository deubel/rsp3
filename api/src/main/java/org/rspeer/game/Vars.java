package org.rspeer.game;

import org.rspeer.commons.Mapping;
import org.rspeer.commons.IntToIntFunction;
import jag.game.type.RSVarpbit;

public class Vars {

    public static final int MAX_VARP = 4000;
    public static final int[] BIT_MASKS = new int[32];

    static {
        int delta = 2;
        for (int mask = 0; mask < 32; ++mask) {
            BIT_MASKS[mask] = delta - 1;
            delta += delta;
        }
    }

    public static int[] getAll() {
        int[] varps = Game.getClient().getVarps();
        return varps != null ? varps : new int[0];
    }

    public static int get(int index) {
        return get(Type.VARP, index);
    }

    public static int get(Type type, int index) {
        return type.provider.apply(index);
    }

    private static int getVarp(int index) {
        int[] varps = getAll();
        if (index < MAX_VARP && index < varps.length && index >= 0) {
            return varps[index];
        }
        return -1;
    }

    public enum Type {

        VARP(Vars::getVarp),
        VARC(Type::unsupported),
        VARBIT(x -> Mapping.orM1(() -> Definitions.getVarpbit(x), RSVarpbit::getValue));

        private final IntToIntFunction provider;

        Type(IntToIntFunction provider) {
            this.provider = provider;
        }

        private static int unsupported(int x) {
            throw new UnsupportedOperationException();
        }
    }
}
