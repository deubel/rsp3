package org.rspeer.game.action.tree;

import org.rspeer.game.action.Action;
import org.rspeer.game.action.ActionOpcode;
import org.rspeer.game.position.Position;
import org.rspeer.game.scene.Scene;

public class WalkAction extends Action {

    private final Position destination;

    public WalkAction(ActionOpcode opcode, int sceneX, int sceneY) {
        this(opcode.getId(), sceneX, sceneY);
    }

    public WalkAction(int opcode, int sceneX, int sceneY) {
        super(opcode, 0, sceneX, sceneY);
        destination = Position.fromRelative(secondary, tertiary, Scene.getFloorLevel());
    }

    public Position getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(super.toString());
        builder.append("[x=")
                .append(destination.getX())
                .append(",y=")
                .append(destination.getY());
        return builder.append("]").toString();
    }
}
