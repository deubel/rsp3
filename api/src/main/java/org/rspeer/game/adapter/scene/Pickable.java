package org.rspeer.game.adapter.scene;

import org.rspeer.game.Definitions;
import org.rspeer.game.action.Action;
import org.rspeer.game.action.ActionOpcode;
import org.rspeer.game.action.tree.PickableAction;
import org.rspeer.game.adapter.component.Inventory;
import org.rspeer.game.adapter.scene.definition.ItemDefinition;
import org.rspeer.game.adapter.type.Identifiable;
import org.rspeer.game.adapter.type.Nameable;
import org.rspeer.commons.StringCommons;
import org.rspeer.game.component.tab.Magic;
import org.rspeer.game.position.Position;
import jag.game.scene.entity.RSPickable;

import java.util.Collections;
import java.util.List;

public class Pickable extends Entity<RSPickable> implements Identifiable, Nameable {

    private final Position position;

    public Pickable(RSPickable provider, Position position) {
        super(provider);
        this.position = position;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public Action actionOf(String action) {
        if (action.equalsIgnoreCase("Examine")) {
            return new PickableAction(ActionOpcode.EXAMINE_PICKABLE, getId(), getPosition());
        }

        if (action.equalsIgnoreCase("Take")) {
            return new PickableAction(ActionOpcode.PICKABLE_ACTION_2, getId(), getPosition());
        }

        if (Magic.isSpellSelected() && action.equalsIgnoreCase("Cast")) {
            return new PickableAction(ActionOpcode.SPELL_ON_PICKABLE, getId(), getPosition());
        }

        if (Inventory.isItemSelected() && action.equalsIgnoreCase("Use")) {
            return new PickableAction(ActionOpcode.ITEM_ON_PICKABLE, getId(), getPosition());
        }

        String[] rawActions = getRawActions();
        for (int i = 0; i < rawActions.length; i++) {
            String raw = rawActions[i];
            if (raw == null) {
                continue;
            }

            if (StringCommons.replaceJagspace(raw).equalsIgnoreCase(action)) {
                ActionOpcode opcode = ActionOpcode.valueOf(ActionOpcode.PICKABLE_ACTION_0.getId() + i);
                return new PickableAction(opcode, getId(), getPosition());
            }
        }

        return null;
    }

    @Override
    public String[] getRawActions() {
        ItemDefinition id = getDefinition();
        if (id != null) {
            return id.getRawActions();
        }

        return new String[0];
    }

    @Override
    public List<String> getActions() {
        ItemDefinition id = getDefinition();
        if (id != null) {
            return id.getActions();
        }

        return Collections.emptyList();
    }

    public ItemDefinition getDefinition() {
        RSPickable provider = getProvider();
        return provider != null ? Definitions.getItem(provider.getId()) : null;
    }

    @Override
    public int getId() {
        RSPickable provider = getProvider();
        return provider != null ? provider.getId() : -1;
    }

    public int getStackSize() {
        RSPickable provider = getProvider();
        return provider != null ? provider.getStackSize() : -1;
    }

    @Override
    public String getName() {
        ItemDefinition id = getDefinition();
        return id != null ? id.getName() : "";
    }
}
