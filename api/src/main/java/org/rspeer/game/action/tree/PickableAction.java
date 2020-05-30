package org.rspeer.game.action.tree;

import org.rspeer.game.action.Action;
import org.rspeer.game.action.ActionOpcode;
import org.rspeer.game.position.Position;
import org.rspeer.game.position.RelativePosition;

public class PickableAction extends Action {

    public PickableAction(ActionOpcode opcode, int id, Position position) {
        this(opcode, id, (RelativePosition) position.toScene());
    }

    public PickableAction(ActionOpcode opcode, int id, RelativePosition position) {
        this(opcode, id, position.getX(), position.getY());
    }

    public PickableAction(ActionOpcode opcode, int id, int sceneX, int sceneY) {
        super(opcode, id, sceneX, sceneY);
    }

    public PickableAction(int opcode, int id, int sceneX, int sceneY) {
        super(opcode, id, sceneX, sceneY);
    }
}
