package org.rspeer.game.action.tree;

import org.rspeer.game.action.ActionOpcode;

public class ButtonAction extends ComponentAction {

    public ButtonAction(int opcode, int subComponentIndex, int componentUid) {
        super(opcode, 0, subComponentIndex, componentUid);
    }

    public ButtonAction(ActionOpcode opcode, int subComponentIndex, int componentUid) {
        super(opcode, 0, subComponentIndex, componentUid);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(super.toString())
                .append("[group=")
                .append(getGroupIndex())
                .append(",component=")
                .append(getComponentIndex());

        if (getSubComponentIndex() != -1) {
            builder.append(",subcomponent=")
                    .append(getSubComponentIndex());
        }

        return builder.append("]").toString();
    }
}

