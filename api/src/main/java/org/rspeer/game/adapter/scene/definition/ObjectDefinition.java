package org.rspeer.game.adapter.scene.definition;

import org.rspeer.game.Definitions;
import jag.game.type.RSObjectDefinition;

public class ObjectDefinition extends TransformableDefinition<RSObjectDefinition, ObjectDefinition> {

    public ObjectDefinition(RSObjectDefinition provider) {
        super(provider, Definitions::getObject);
    }

    public int getMapFunction() {
        RSObjectDefinition provider = getProvider();
        return provider != null ? provider.getMapFunction() : -1;
    }

    public int getMapScene() {
        RSObjectDefinition provider = getProvider();
        return provider != null ? provider.getMapSceneId() : -1;
    }

    public short[] getColors() {
        RSObjectDefinition provider = getProvider();
        if (provider == null) {
            return new short[0];
        }

        short[] colors = provider.getNewColors();
        return colors != null ? colors : new short[0];
    }
}
