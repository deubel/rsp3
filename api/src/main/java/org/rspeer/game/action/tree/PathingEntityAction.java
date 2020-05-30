package org.rspeer.game.action.tree;

import org.rspeer.game.action.Action;
import org.rspeer.game.action.ActionOpcode;
import org.rspeer.game.adapter.scene.PathingEntity;

public abstract class PathingEntityAction<T extends PathingEntity> extends Action {

    protected PathingEntityAction(ActionOpcode opcode, int index) {
        super(opcode, index, 0, 0);
    }

    protected PathingEntityAction(int opcode, int index) {
        super(opcode, index, 0, 0);
    }

    public int getIndex() {
        return primary;
    }

    public abstract T getSource();
}
