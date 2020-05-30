package org.rspeer.game.adapter.scene;

import org.rspeer.game.Game;
import org.rspeer.game.action.Action;
import org.rspeer.game.action.ActionOpcode;
import org.rspeer.game.action.tree.PlayerAction;
import org.rspeer.commons.StringCommons;
import org.rspeer.game.adapter.component.Inventory;
import org.rspeer.game.component.tab.Magic;
import jag.game.scene.entity.RSPlayer;

import java.util.ArrayList;
import java.util.List;

public class Player extends PathingEntity<RSPlayer> {

    private final int index;

    public Player(RSPlayer provider, int index) {
        super(provider);
        this.index = index;
    }

    @Override
    public PlayerAction actionOf(String action) {
        if (action.equalsIgnoreCase("Cast") && Magic.isSpellSelected()) {
            return new PlayerAction(ActionOpcode.SPELL_ON_PLAYER, getIndex());
        } else if (action.equalsIgnoreCase("Use") && Inventory.isItemSelected()) {
            return new PlayerAction(ActionOpcode.ITEM_ON_PLAYER, getIndex());
        }

        int index = Action.indexOf(getRawActions(), action);
        return index >= 0 ? new PlayerAction(ActionOpcode.PLAYER_ACTION_0.getId() + index, getIndex()) : null;
    }

    @Override
    public int getId() {
        return index;
    }

    @Override
    public String[] getRawActions() {
        String[] raw = Game.getClient().getPlayerActions();
        return raw != null ? raw : new String[0];
    }

    public int getCombatLevel() {
        RSPlayer provider = getProvider();
        return provider != null ? provider.getCombatLevel() : -1;
    }

    @Override
    public List<String> getActions() {
        List<String> actions = new ArrayList<>();
        for (String action : getRawActions()) {
            if (action != null) {
                actions.add(StringCommons.replaceJagspace(action));
            }
        }
        return actions;
    }

    @Override
    public String getName() {
        RSPlayer provider = getProvider();
        return provider != null ? provider.getName() : "";
    }

    @Override
    public int getIndex() {
        return index;
    }
}
