package org.rspeer.game.action.tree;

import org.rspeer.game.action.Action;
import org.rspeer.game.action.ActionOpcode;
import org.rspeer.game.adapter.component.InterfaceComponent;
import org.rspeer.game.component.Interfaces;

public abstract class ComponentAction extends Action {

    protected ComponentAction(int opcode, int primary, int secondary, int tertiary) {
        super(opcode, primary, secondary, tertiary);
    }

    protected ComponentAction(ActionOpcode opcode, int primary, int secondary, int tertiary) {
        super(opcode, primary, secondary, tertiary);
    }

    public int getUid() {
        return tertiary;
    }

    public int getSubComponentIndex() {
        return secondary;
    }

    public boolean isSubComponent() {
        return secondary != -1;
    }

    public int getGroupIndex() {
        return getUid() >>> 16;
    }

    public int getComponentIndex() {
        return getUid() & 0xFFFF;
    }

    public InterfaceComponent getSource() {
        return Interfaces.getDirect(getGroupIndex(), getComponentIndex(), getSubComponentIndex());
    }
}
