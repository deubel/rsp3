package org.rspeer.game.adapter.scene.definition;

import org.rspeer.game.Vars;
import jag.game.type.RSTransformableDefinition;

import java.util.function.Function;

public abstract class TransformableDefinition<P extends RSTransformableDefinition, T extends TransformableDefinition> extends Definition<P> {

    private final Function<Integer, T> transform;

    protected TransformableDefinition(P provider, Function<Integer, T> transform) {
        super(provider);
        this.transform = transform;
    }

    public int[] getTransformIds() {
        P provider = getProvider();
        return provider != null ? provider.getTransformIds() : new int[0];
    }

    public int getVarbitIndex() {
        P provider = getProvider();
        return provider != null ? provider.getVarpbitIndex() : -1;
    }

    public int getVarpIndex() {
        P provider = getProvider();
        return provider != null ? provider.getVarpIndex() : -1;
    }

    public T transform() {
        int[] transformIds = getTransformIds();

        if (transformIds == null) {
            return null;
        }

        int varbitIndex = getVarbitIndex();
        int varpIndex = getVarpIndex();
        int transformIndex = -1;
        int transformedId;

        if (varbitIndex != -1) {
            transformIndex = Vars.get(Vars.Type.VARBIT, varbitIndex);
        } else if (varpIndex != -1) {
            transformIndex = Vars.get(varpIndex);
        }

        if (transformIndex >= 0 && transformIndex < transformIds.length) {
            transformedId = transformIds[transformIndex];
        } else {
            transformedId = transformIds[transformIds.length - 1];
        }

        return transformedId != -1 ? transform.apply(transformedId) : null;
    }

}
