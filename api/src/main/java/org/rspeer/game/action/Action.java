package org.rspeer.game.action;

import org.rspeer.commons.StringCommons;
import org.rspeer.game.action.tree.*;

public abstract class Action {

    protected final ActionOpcode opcode;
    protected final int primary;
    protected final int secondary, tertiary;

    protected Action(ActionOpcode opcode, int primary, int secondary, int tertiary) {
        this.opcode = opcode;
        this.primary = primary;
        this.secondary = secondary;
        this.tertiary = tertiary;
    }

    protected Action(int opcode, int primary, int secondary, int tertiary) {
        this(ActionOpcode.valueOf(opcode), primary, secondary, tertiary);
    }

    public static int indexOf(String[] actions, String action) {
        if (action == null || actions == null) {
            return -1;
        }

        for (int i = 0; i < actions.length; i++) {
            String current = actions[i];
            if (StringCommons.sanitize(action).equalsIgnoreCase(StringCommons.sanitize(current))) {
                return i;
            }
        }
        return -1;
    }

    public static Action valueOf(int op, int primary, int secondary, int tertiary) {
        if (op >= 2000) {
            op -= 2000;
        }

        ActionOpcode resolved = ActionOpcode.valueOf(op);
        if (resolved != null) {
            switch (resolved.getTargetType()) {
                case NPC:
                    return new NpcAction(op, primary);

                case PLAYER:
                    return new PlayerAction(op, primary);

                case OBJECT:
                    return new ObjectAction(op, primary, secondary, tertiary);

                case COMPONENT:
                    return new DefaultComponentAction(primary + 1, secondary, tertiary);

                case BUTTON:
                    return new ButtonAction(op, secondary, tertiary);

                case ITEM:
                    return new ItemAction(op, primary, secondary, tertiary);

                case PICKABLE:
                    return new PickableAction(op, primary, secondary, tertiary);

                case POSITION:
                    if (op != ActionOpcode.WALK_HERE.getId()) {
                        return new WalkAction(op, secondary, tertiary);
                    }
                    break;
            }
        }
        return null;
    }

    public ActionOpcode getOpcode() {
        return opcode;
    }

    public int getPrimary() {
        return primary;
    }

    public int getSecondary() {
        return secondary;
    }

    public int getTertiary() {
        return tertiary;
    }

    @Override
    public String toString() {
        return String.format("%s<%s>", getClass().getSimpleName(), opcode);
    }
}
