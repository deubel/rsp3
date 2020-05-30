package org.rspeer.game.action.tree;

import org.rspeer.game.action.Action;
import org.rspeer.game.action.ActionOpcode;
import org.rspeer.game.adapter.component.InterfaceComponent;
import org.rspeer.game.component.Interfaces;
import org.rspeer.game.component.Item;

public class ItemAction extends Action {

    public ItemAction(ActionOpcode opcode, int primary, int secondary, int tertiary) {
        super(opcode, primary, secondary, tertiary);
    }

    public ItemAction(int opcode, int primary, int secondary, int tertiary) {
        super(opcode, primary, secondary, tertiary);
    }

    public int getId() {
        return primary;
    }

    public int getIndex() {
        return secondary;
    }

    public Item getSource() {
        InterfaceComponent component = getComponent();
        if (component == null) {
            return null;
        }
        return component.getType() == InterfaceComponent.Type.TABLE
                ? Item.getTableElement(component, secondary)
                : Item.direct(component);
    }

    //TODO this can be better, need better way to determine if subcomponent or not
    public InterfaceComponent getComponent() {
        InterfaceComponent component = Interfaces.getDirect(tertiary >>> 16, tertiary & 0xFFFF);
        if (component != null) {
            InterfaceComponent subComponent = component.getSubComponent(secondary);
            if (subComponent != null) {
                return subComponent;
            }
        }
        return component;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(super.toString());
        Item item = getSource();
        InterfaceComponent component = getComponent();
        if (item != null && component != null) {
            builder.append("[")
                    .append("id=")
                    .append(item.getId())
                    .append(",index=")
                    .append(item.getIndex())
                    .append(",address=")
                    .append(component.toAddress().toString())
                    .append("]");
        }
        return builder.toString();
    }
}
