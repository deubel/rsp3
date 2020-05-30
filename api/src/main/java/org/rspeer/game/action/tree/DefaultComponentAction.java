package org.rspeer.game.action.tree;

import org.rspeer.game.action.ActionOpcode;

public class DefaultComponentAction extends ComponentAction {

    public DefaultComponentAction(int actionIdx, int subIdx, int cmpUid) {
        super(actionIdx > 5 ? ActionOpcode.COMPONENT_ACTION_2 : ActionOpcode.COMPONENT_ACTION, actionIdx, subIdx, cmpUid);
    }

    public DefaultComponentAction(ActionOpcode opcode, int actionIdx, int subIdx, int cmpUid) {
        super(opcode, actionIdx, subIdx, cmpUid);
    }

    public int getActionIndex() {
        return primary;
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

        return builder.append(",actionIndex=")
                .append(getActionIndex())
                .append("]").toString();
    }
}
