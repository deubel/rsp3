package org.rspeer.game.adapter.scene;

import org.rspeer.game.action.Action;
import org.rspeer.game.adapter.type.Identifiable;
import org.rspeer.game.position.Position;
import jag.game.scene.entity.RSEffectObject;

import java.util.List;

public class EffectObject extends Entity<RSEffectObject> implements Identifiable {

    public EffectObject(RSEffectObject provider) {
        super(provider);
    }

    @Override
    public Action actionOf(String action) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String[] getRawActions() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> getActions() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getId() {
        RSEffectObject provider = getProvider();
        return provider != null ? provider.getId() : -1;
    }

    @Override
    public Position getPosition() {
        RSEffectObject provider = getProvider();
        if (provider == null) {
            return new Position(-1, -1);
        }
        return Position.fromAbsolute(provider.getAbsoluteX(), provider.getAbsoluteY(), provider.getFloorLevel());
    }
}
