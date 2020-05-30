package org.rspeer.game.adapter.scene;

import org.rspeer.game.Definitions;
import org.rspeer.game.action.Action;
import org.rspeer.game.action.ActionOpcode;
import org.rspeer.game.action.tree.NpcAction;
import org.rspeer.game.adapter.component.Inventory;
import org.rspeer.game.adapter.scene.definition.NpcDefinition;
import org.rspeer.commons.StringCommons;
import org.rspeer.game.component.tab.Magic;
import jag.game.scene.entity.RSNpc;
import jag.game.type.RSNpcDefinition;

import java.util.Collections;
import java.util.List;

public class Npc extends PathingEntity<RSNpc> {

    private final int index;

    public Npc(RSNpc provider, int index) {
        super(provider);
        this.index = index;
    }

    public NpcDefinition getDefinition() {
        RSNpc provider = getProvider();
        if (provider == null) {
            return null;
        }

        RSNpcDefinition def = provider.getDefinition();
        return def != null ? Definitions.getTransformable(provider.getDefinition().getId(), NpcDefinition.class) : null;
    }

    @Override
    public int getId() {
        RSNpc provider = getProvider();
        if (provider == null) {
            return -1;
        }

        NpcDefinition definition = getDefinition();
        return definition != null ? definition.getId() : -1;
    }

    @Override
    public String getName() {
        RSNpc provider = getProvider();
        if (provider == null) {
            return "";
        }

        NpcDefinition definition = getDefinition();
        return definition != null ? definition.getName() : "";
    }

    public String getOverheadText() {
        RSNpc provider = getProvider();
        if (provider == null) {
            return "";
        }

        String text = provider.getOverheadText();
        return text != null ? StringCommons.sanitize(text) : "";
    }

    @Override
    public String[] getRawActions() {
        RSNpc provider = getProvider();
        if (provider == null) {
            return new String[0];
        }

        NpcDefinition definition = getDefinition();
        return definition != null ? definition.getRawActions() : new String[0];
    }

    @Override
    public List<String> getActions() {
        RSNpc provider = getProvider();
        if (provider == null) {
            return Collections.emptyList();
        }

        NpcDefinition definition = getDefinition();
        return definition != null ? definition.getActions() : Collections.emptyList();
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public NpcAction actionOf(String action) {
        if (action.equalsIgnoreCase("Use") && Inventory.isItemSelected()) {
            return new NpcAction(ActionOpcode.ITEM_ON_NPC, getIndex());
        } else if (action.equalsIgnoreCase("Cast") && Magic.isSpellSelected()) {
            return new NpcAction(ActionOpcode.SPELL_ON_NPC, getIndex());
        } else if (action.equalsIgnoreCase("Examine")) {
            return new NpcAction(ActionOpcode.EXAMINE_NPC, getIndex());
        }

        int index = Action.indexOf(getRawActions(), action);
        return index >= 0 ? new NpcAction(ActionOpcode.NPC_ACTION_0.getId() + index, getIndex()) : null;
    }
}
