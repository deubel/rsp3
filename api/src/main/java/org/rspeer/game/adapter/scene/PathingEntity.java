package org.rspeer.game.adapter.scene;

import org.rspeer.commons.StringCommons;
import org.rspeer.game.Game;
import org.rspeer.game.adapter.type.Animable;
import org.rspeer.game.adapter.type.Identifiable;
import org.rspeer.game.adapter.type.Mobile;
import org.rspeer.game.adapter.type.Nameable;
import org.rspeer.game.effect.Animation;
import org.rspeer.game.effect.Direction;
import org.rspeer.game.effect.Stance;
import org.rspeer.game.position.Position;
import org.rspeer.game.scene.Npcs;
import org.rspeer.game.scene.Players;
import org.rspeer.game.scene.Scene;
import jag.RSLinkedList;
import jag.RSNode;
import jag.game.RSHealthBar;
import jag.game.RSHitUpdate;
import jag.game.scene.entity.RSPathingEntity;

public abstract class PathingEntity<P extends RSPathingEntity> extends Entity<P>
        implements Nameable, Identifiable, Animable, Mobile {

    private static final int DEFAULT_MAX_HEALTHBAR_WIDTH = 30;

    protected PathingEntity(P provider) {
        super(provider);
    }

    public Animation getAnimation() {
        P provider = getProvider();
        if (provider == null) {
            return null;
        }

        int id = provider.getAnimation();
        return id != -1 ? new Animation(id, provider.getAnimationFrame()) : null;
    }

    public Animation getStance() {
        P provider = getProvider();
        if (provider == null) {
            return null;
        }

        int id = provider.getStance();
        return id != -1 ? new Stance(this, id, provider.getStanceFrame()) : null;
    }

    public Animation getEffect() {
        P provider = getProvider();
        if (provider == null) {
            return null;
        }

        int id = provider.getEffect();
        return id != -1 ? new Animation(id, provider.getEffectFrame()) : null;
    }

    public Position getPosition() {
        P provider = getProvider();
        if (provider == null) {
            return new Position(-1, -1);
        }

        return Position.fromAbsolute(provider.getAbsoluteX(), provider.getAbsoluteY(), Scene.getFloorLevel());
    }

    public Direction getDirection() {
        P provider = getProvider();
        if (provider == null) {
            return Direction.EAST; //TODO?
        }

        int orientation = provider.getOrientation();
        Direction[] directions = Direction.values();
        return directions[orientation / 256];
    }

    public boolean isMoving() {
        P provider = getProvider();
        return provider != null && provider.getRouteWaypointCount() > 0;
    }

    public int getTargetIndex() {
        P provider = getProvider();
        return provider != null ? provider.getTargetIndex() : -1;
    }

    public String getOverheadText() {
        P provider = getProvider();
        if (provider == null) {
            return "";
        }

        String text = provider.getOverheadText();
        return text != null ? StringCommons.sanitize(provider.getOverheadText()) : "";
    }

    public RSHealthBar getHealthBar() {
        P provider = getProvider();
        if (provider == null) {
            return null;
        }

        RSLinkedList<RSHealthBar> bars = provider.getHealthBars();
        return bars != null ? bars.head() : null;
    }

    private int getMaxHealthBarWidth() {
        RSHealthBar provider = getHealthBar();
        if (provider == null || provider.getDefinition() == null) {
            return DEFAULT_MAX_HEALTHBAR_WIDTH;
        }
        return provider.getDefinition().getMaxWidth();
    }

    /**
     * Note: This method will only return correctly when the health bar is present.
     * For local player health it is recommended that you use the Health class instead
     *
     * @return The current health percent
     */
    public int getHealthPercent() {
        RSHealthBar provider = getHealthBar();
        if (provider != null) {
            RSNode update = provider.getHitsplats().getSentinel().getNext();
            if (update instanceof RSHitUpdate) {
                int max = getMaxHealthBarWidth();
                int width = ((RSHitUpdate) update).getStartWidth();
                return (int) Math.ceil(100.0 * width / max);
            }
        }
        return 100;
    }

    public PathingEntity<?> getTarget() {
        int index = getTargetIndex();
        if (index == -1) {
            return null;
        } else if (index < 32768) {
            return Npcs.getAt(index);
        }
        index -= 32768;
        return index == Game.getClient().getPlayerIndex() ? Players.getLocal() : Players.getAt(index);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PathingEntity<?> && ((PathingEntity<?>) o).getIndex() == getIndex();
    }

    public abstract int getIndex();

    public boolean isHealthBarVisible() {
        P provider = getProvider();
        if (provider == null) {
            return false;
        }

        for (int cycle : provider.getHitsplatCycles()) {
            if (cycle + 250 > Game.getEngineCycle()) {
                return true;
            }
        }
        return false;
    }
}
