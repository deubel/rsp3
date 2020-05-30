package org.rspeer.game.adapter.component.tab;

import org.rspeer.game.Game;
import org.rspeer.game.action.ActionOpcode;
import org.rspeer.game.action.tree.ItemAction;
import org.rspeer.game.action.tree.NpcAction;
import org.rspeer.game.action.tree.ObjectAction;
import org.rspeer.game.action.tree.PickableAction;
import org.rspeer.game.adapter.component.InterfaceComponent;
import org.rspeer.game.adapter.component.Inventory;
import org.rspeer.game.adapter.scene.Npc;
import org.rspeer.game.adapter.scene.Pickable;
import org.rspeer.game.adapter.scene.Player;
import org.rspeer.game.adapter.scene.SceneObject;
import org.rspeer.game.adapter.type.Interactable;
import org.rspeer.game.component.InventoryType;
import org.rspeer.game.component.Item;
import org.rspeer.game.query.component.ItemQuery;
import jag.game.RSInventory;

import java.util.function.Function;

public class Backpack extends Inventory {

    public Backpack(RSInventory provider) {
        super(provider, InventoryType.BACKPACK.getFormat(), InventoryType.BACKPACK.getQueries());
    }

    public boolean use(Function<ItemQuery, Item> function, Interactable target) {
        if (function == null || target == null) {
            return false;
        }

        Item item = function.apply(query());
        if (item != null && item.interact("Use")) {
            if (target instanceof Npc) {
                Game.queueAction(new NpcAction(ActionOpcode.ITEM_ON_NPC, ((Npc) target).getIndex()));
                return true;
            }

            if (target instanceof Player) {
                Game.queueAction(new NpcAction(ActionOpcode.ITEM_ON_PLAYER, ((Player) target).getIndex()));
                return true;
            }

            if (target instanceof Pickable) {
                Pickable pickable = (Pickable) target;
                Game.queueAction(new PickableAction(ActionOpcode.ITEM_ON_PICKABLE, pickable.getId(), pickable.getPosition()));
                return true;
            }

            if (target instanceof Item) {
                Item itemB = (Item) target;
                InterfaceComponent component = itemB.getComponent();
                if (component == null) {
                    return false;
                }

                Game.queueAction(new ItemAction(ActionOpcode.ITEM_ON_ITEM, itemB.getId(), itemB.getIndex(), component.getUid()));
                return true;
            }

            if (target instanceof SceneObject) {
                SceneObject object = (SceneObject) target;
                Game.queueAction(new ObjectAction(ActionOpcode.ITEM_ON_OBJECT, object.getId(), object.getPosition()));
                return true;
            }
        }
        return false;
    }
}
