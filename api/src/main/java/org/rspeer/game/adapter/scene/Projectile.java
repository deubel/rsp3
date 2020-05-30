package org.rspeer.game.adapter.scene;

import org.rspeer.game.Game;
import org.rspeer.game.action.Action;
import org.rspeer.game.adapter.type.Identifiable;
import org.rspeer.game.adapter.type.Mobile;
import org.rspeer.game.position.Position;
import org.rspeer.game.scene.Npcs;
import org.rspeer.game.scene.Players;
import org.rspeer.game.scene.Scene;
import jag.game.scene.entity.RSProjectile;

import java.util.List;

public class Projectile extends Entity<RSProjectile> implements Identifiable, Mobile {

    public Projectile(RSProjectile provider) {
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
        RSProjectile provider = getProvider();
        return provider != null ? provider.getId() : -1;
    }

    @Override
    public Position getPosition() {
        RSProjectile provider = getProvider();
        if (provider == null) {
            return new Position(-1, -1);
        }
        return Position.fromAbsolute((int) provider.getAbsoluteX(), (int) provider.getAbsoluteY(), Scene.getFloorLevel());
    }

    public Position getStartPosition() {
        RSProjectile provider = getProvider();
        if (provider == null) {
            return new Position(-1, -1);
        }

        return Position.fromAbsolute(provider.getStartX(), provider.getStartY(), Scene.getFloorLevel());
    }

    public boolean isMoving() {
        RSProjectile provider = getProvider();
        return provider != null && provider.isInMotion();
    }

    public PathingEntity<?> getTarget() {
        RSProjectile provider = getProvider();
        if (provider == null) {
            return null;
        }

        int index = provider.getTargetIndex();
        if (index == 0) {
            return null;
        } else if (index > 0) {
            return Npcs.getAt(index - 1);
        }
        index = -index - 1;
        return index == Game.getClient().getPlayerIndex() ? Players.getLocal() : Players.getAt(index);
    }
}
