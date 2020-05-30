package org.rspeer.game;

public class Cutscene {

    public static boolean isActive() {
        return Vars.get(Vars.Type.VARBIT, 542) == 1
                && Vars.get(Vars.Type.VARBIT, 4606) == 1;
    }
}
