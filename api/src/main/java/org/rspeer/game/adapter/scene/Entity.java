package org.rspeer.game.adapter.scene;

import org.rspeer.game.adapter.Adapter;
import org.rspeer.game.adapter.type.Interactable;
import org.rspeer.game.adapter.type.SceneNode;
import org.rspeer.game.position.Position;
import jag.game.scene.entity.RSEntity;

public abstract class Entity<P extends RSEntity> extends Adapter<P> implements Interactable, SceneNode {

    protected Entity(P provider) {
        super(provider);
    }

    public int getHeight() {
        P provider = getProvider();
        return provider != null ? provider.getHeight() : -1;
    }

    public abstract Position getPosition();
}
