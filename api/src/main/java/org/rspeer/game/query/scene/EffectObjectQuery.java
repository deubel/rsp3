package org.rspeer.game.query.scene;

import org.rspeer.commons.ArrayUtils;
import org.rspeer.game.adapter.scene.EffectObject;
import org.rspeer.game.adapter.type.Identifiable;
import org.rspeer.game.query.results.SceneNodeQueryResults;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class EffectObjectQuery extends SceneNodeQuery<EffectObject, EffectObjectQuery>
        implements Identifiable.Query<EffectObjectQuery> {

    private final Supplier<List<EffectObject>> provider;

    private int[] ids = null;

    public EffectObjectQuery(Supplier<List<EffectObject>> provider) {
        this.provider = provider;
    }

    @Override
    public Supplier<List<EffectObject>> getDefaultProvider() {
        return provider;
    }

    @Override
    protected SceneNodeQueryResults<EffectObject> createQueryResults(Collection<EffectObject> raw) {
        return new SceneNodeQueryResults<>(raw);
    }

    @Override
    public EffectObjectQuery self() {
        return this;
    }

    @Override
    public EffectObjectQuery ids(int... ids) {
        this.ids = ids;
        return self();
    }

    @Override
    public boolean test(EffectObject e) {
        if (ids != null && !ArrayUtils.contains(ids, e.getId())) {
            return false;
        }

        return super.test(e);
    }
}
