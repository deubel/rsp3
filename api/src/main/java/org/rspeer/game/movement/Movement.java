package org.rspeer.game.movement;

import org.rspeer.game.Game;
import org.rspeer.game.Vars;
import org.rspeer.game.action.ActionOpcode;
import org.rspeer.game.action.tree.WalkAction;
import org.rspeer.game.adapter.component.InterfaceComponent;
import org.rspeer.game.adapter.type.SceneNode;
import org.rspeer.game.component.Interfaces;
import org.rspeer.game.position.Position;
import org.rspeer.game.scene.Scene;
import jag.game.RSClient;

public class Movement {

    private static final int RUN_VAR = 173;
    private static final int STAMINA_BIT = 25;

    public static void walkTowards(SceneNode destination) {
        Position dest = destination.getPosition().toScene();
        int x = normalize(dest.getX());
        int y = normalize(dest.getY());
        Game.queueAction(new WalkAction(ActionOpcode.WALK, x, y));
    }

    private static int normalize(int value) {
        if (value < 0) {
            return value;
        } else if (value > 103) {
            return value;
        }
        return value;
    }

    public static boolean isStaminaEnhancementActive() {
        return Vars.get(Vars.Type.VARBIT, STAMINA_BIT) == 1;
    }

    public static boolean isRunEnabled() {
        return Vars.get(RUN_VAR) == 1;
    }

    public static int getRunEnergy() {
        return Game.getClient().getEnergy();
    }

    public static boolean toggleRun(boolean on) {
        if (Movement.isRunEnabled() == on) {
            return true;
        }
        InterfaceComponent btn = Interfaces.getDirect(160, 22);
        return btn != null && btn.interact(x -> true);
    }

    public static Position getDestination() {
        if (!isDestinationSet()) {
            return null;
        }
        Position base = Scene.getBase();
        RSClient client = Game.getClient();
        return new Position(base.getX() + client.getDestinationX(), base.getY() + client.getDestinationY(), base.getFloorLevel());
    }

    public static boolean isDestinationSet() {
        return Game.getClient().getDestinationX() > 0;
    }
}
