package org.rspeer.game.adapter.scene;

import org.rspeer.game.Definitions;
import org.rspeer.game.action.Action;
import org.rspeer.game.action.ActionOpcode;
import org.rspeer.game.action.tree.ObjectAction;
import org.rspeer.game.adapter.component.Inventory;
import org.rspeer.game.adapter.scene.definition.ObjectDefinition;
import org.rspeer.game.adapter.type.Identifiable;
import org.rspeer.game.adapter.type.Nameable;
import org.rspeer.game.component.tab.Magic;
import org.rspeer.game.position.Position;
import org.rspeer.game.scene.Scene;
import jag.game.scene.entity.RSSceneObject;

import java.util.Collections;
import java.util.List;

public class SceneObject extends Entity<RSSceneObject> implements Nameable, Identifiable {

    public SceneObject(RSSceneObject provider) {
        super(provider);
    }

    @Override
    public Position getPosition() {
        RSSceneObject provider = getProvider();
        if (provider == null) {
            return new Position(-1, -1);
        }

        return Position.fromRelative(provider.getSceneX(), provider.getSceneY(), Scene.getFloorLevel());
    }

    public ObjectDefinition getDefinition() {
        ObjectDefinition def = Definitions.getObject(getId());
        return def != null ? Definitions.getTransformable(getId(), ObjectDefinition.class) : null;
    }

    @Override
    public int getId() {
        RSSceneObject provider = getProvider();
        return provider != null ? provider.getId() : -1;
    }

    @Override
    public String getName() {
        ObjectDefinition def = getDefinition();
        return def != null ? def.getName() : "";
    }

    @Override
    public ObjectAction actionOf(String action) {
        Position pos = getPosition().toScene();
        if (action.equalsIgnoreCase("Use") && Inventory.isItemSelected()) {
            return new ObjectAction(ActionOpcode.ITEM_ON_OBJECT, getId(), pos.getX(), pos.getY());
        } else if (action.equalsIgnoreCase("Cast") && Magic.isSpellSelected()) {
            return new ObjectAction(ActionOpcode.SPELL_ON_OBJECT, getId(), pos.getX(), pos.getY());
        } else if (action.equalsIgnoreCase("Examine")) {
            return new ObjectAction(ActionOpcode.EXAMINE_OBJECT, getId(), pos.getX(), pos.getY());
        }

        int index = Action.indexOf(getRawActions(), action);
        if (index >= 0 && index < 4) {
            return new ObjectAction(ActionOpcode.OBJECT_ACTION_0.getId() + index, getId(), pos.getX(), pos.getY());
        } else if (index == 4) {
            return new ObjectAction(ActionOpcode.OBJECT_ACTION_4, getId(), pos.getX(), pos.getY());
        }
        return null;
    }

    @Override
    public String[] getRawActions() {
        ObjectDefinition def = getDefinition();
        return def != null ? def.getRawActions() : new String[0];
    }

    @Override
    public List<String> getActions() {
        ObjectDefinition def = getDefinition();
        return def != null ? def.getActions() : Collections.emptyList();
    }
}
