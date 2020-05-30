package org.rspeer.game.component;

import org.rspeer.commons.Mapping;
import org.rspeer.game.Definitions;
import org.rspeer.game.action.Action;
import org.rspeer.game.action.ActionOpcode;
import org.rspeer.game.action.tree.ItemAction;
import org.rspeer.game.adapter.component.InterfaceComponent;
import org.rspeer.game.adapter.component.Inventory;
import org.rspeer.game.adapter.scene.definition.ItemDefinition;
import org.rspeer.game.adapter.type.Identifiable;
import org.rspeer.game.adapter.type.Interactable;
import org.rspeer.game.adapter.type.Nameable;
import org.rspeer.commons.StringCommons;
import org.rspeer.game.component.tab.Magic;

import java.util.Collections;
import java.util.List;

public class Item implements Identifiable, Nameable, Interactable {

    private static final int[] NULL_ITEM = {6512, -1};

    private final int index;
    private final int id;
    private final int stackSize;

    private final ItemDefinition definition;

    private final InterfaceAddress address;

    private Item(int index, int id, int stackSize, InterfaceAddress address) {
        this.index = index;
        this.id = id;
        this.stackSize = stackSize;
        this.address = address;

        definition = Definitions.getItem(id);
    }

    public static Item unbound(int index, int id, int stackSize) {
        return new Item(index, id, stackSize, null);
    }

    public static Item direct(InterfaceComponent component) {
        int id = component.getProvider().getItemId();
        int stack = component.getProvider().getItemStackSize();
        if (isNullId(id)) {
            return null;
        }
        return new Item(component.getSubComponentIndex(), id, stack, component.toAddress());
    }

    //TODO needs better name, preferably 1 word
    public static Item getTableElement(InterfaceComponent component, int index) {
        if (component.getType() != InterfaceComponent.Type.TABLE) {
            throw new IllegalArgumentException("Did you mean to use Item#direct?");
        }

        int[] ids = component.getProvider().getItemIds();
        int[] stacks = component.getProvider().getItemStackSizes();
        if (index < 0 || index >= ids.length) {
            throw new IllegalArgumentException("Invalid index specified");
        }

        int id = ids[index] - 1;
        if (isNullId(id)) {
            return null;
        }
        return new Item(index, id, stacks[index], component.toAddress());
    }

    public static boolean isNullId(int id) {
        for (int nullId : NULL_ITEM) {
            if (id == nullId) {
                return true;
            }
        }
        return false;
    }

    public InterfaceComponent getComponent() {
        return address != null ? address.resolve() : null;
    }

    public boolean isUnbound() {
        return address == null;
    }

    @Override
    public String getName() {
        return definition != null ? definition.getName() : "";
    }

    public int getIndex() {
        return index;
    }

    @Override
    public int getId() {
        return id;
    }

    public int getStackSize() {
        return stackSize;
    }

    public ItemDefinition getDefinition() {
        return definition;
    }

    public boolean isStackable() {
        return Mapping.orElse(this::getDefinition, ItemDefinition::isStackable);
    }

    public boolean isNoted() {
        return Mapping.orElse(this::getDefinition, ItemDefinition::isNoted);
    }

    @Override
    public Action actionOf(String action) {
        if (isUnbound()) {
            throw new IllegalStateException("Item is not bound to an address");
        }

        InterfaceComponent component = getComponent();
        if (component == null) {
            return null;
        }

        if (component.getType() != InterfaceComponent.Type.TABLE) {
            return component.actionOf(action);
        }

        if (action.equalsIgnoreCase("Examine")) {
            return new ItemAction(ActionOpcode.EXAMINE_ITEM, id, index, component.getUid());
        }

        if (Inventory.isItemSelected() && action.equalsIgnoreCase("Use")) {
            return new ItemAction(ActionOpcode.ITEM_ON_ITEM, id, index, component.getUid());
        }

        if (Magic.isSpellSelected() && action.equalsIgnoreCase("Cast")) {
            return new ItemAction(ActionOpcode.SPELL_ON_ITEM, id, index, component.getUid());
        }

        if (ComponentConfig.isDefinitionActions(component.getConfig())) {
            if (action.equalsIgnoreCase("Drop")) {
                return new ItemAction(ActionOpcode.ITEM_ACTION_4, id, index, component.getUid());
            }
        }

        if (ComponentConfig.allowsUsability(component.getConfig()) && action.equalsIgnoreCase("Use")) {
            return new ItemAction(ActionOpcode.USE_ITEM, id, index, component.getUid());
        }

        String[] invActions = definition != null ? definition.getRawActions() : null;
        if (invActions != null) {
            for (int i = 0; i < invActions.length; i++) {
                String rawAction = invActions[i];
                if (rawAction == null) {
                    continue;
                }

                if (StringCommons.replaceJagspace(rawAction).equalsIgnoreCase(action)) {
                    return new ItemAction(ActionOpcode.ITEM_ACTION_0.getId() + i, id, index, component.getUid());
                }
            }
        }

        String[] tableActions = component.getProvider().getTableActions();
        if (tableActions != null) {
            for (int i = 0; i < tableActions.length; i++) {
                String tableAction = tableActions[i];
                if (tableAction == null) {
                    continue;
                }

                if (StringCommons.replaceJagspace(tableAction).equalsIgnoreCase(action)) {
                    return new ItemAction(ActionOpcode.TABLE_ACTION_0.getId() + i, id, index, component.getUid());
                }
            }
        }

        return null;
    }

    @Override
    public String[] getRawActions() {
        if (definition != null) {
            return definition.getRawActions();
        }
        return new String[0];
    }

    @Override
    public List<String> getActions() {
        if (isUnbound()) {
            return definition != null ? definition.getActions() : Collections.emptyList();
        }

        InterfaceComponent cmp = getComponent();
        if (cmp == null) {
            return definition != null ? definition.getActions() : Collections.emptyList();
        } else if (cmp.getType() == InterfaceComponent.Type.TABLE) {
            return ComponentConfig.isDefinitionActions(cmp.getConfig()) ? definition.getActions() : cmp.getTableActions();
        }

        return cmp.getActions();
    }

    @Override
    public String toString() {
        return id + " (" + stackSize + ")";
    }
}
