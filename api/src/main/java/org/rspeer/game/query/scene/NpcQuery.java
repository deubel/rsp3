package org.rspeer.game.query.scene;

import org.rspeer.commons.ArrayUtils;
import org.rspeer.game.adapter.scene.Npc;
import org.rspeer.game.adapter.type.Actionable;
import org.rspeer.game.adapter.type.Identifiable;
import org.rspeer.game.query.results.SceneNodeQueryResults;
import jag.game.scene.entity.RSNpc;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class NpcQuery extends PathingEntityQuery<RSNpc, Npc, NpcQuery> implements
        Actionable.Query<NpcQuery>,
        Identifiable.Query<NpcQuery> {

    private final Supplier<List<Npc>> provider;

    private String[] actions = null;
    private int[] ids = null;

    public NpcQuery(Supplier<List<Npc>> provider) {
        this.provider = provider;
    }

    @Override
    public Supplier<List<Npc>> getDefaultProvider() {
        return provider;
    }

    @Override
    protected SceneNodeQueryResults<Npc> createQueryResults(Collection<Npc> raw) {
        return new SceneNodeQueryResults<>(raw);
    }

    @Override
    public NpcQuery self() {
        return this;
    }

    @Override
    public NpcQuery actions(String... actions) {
        this.actions = actions;
        return self();
    }

    @Override
    public NpcQuery ids(int... ids) {
        this.ids = ids;
        return self();
    }

    @Override
    public boolean test(Npc e) {
        if (ids != null && !ArrayUtils.contains(ids, e.getId())) {
            return false;
        }

        if (actions != null && !e.containsAction(x -> ArrayUtils.containsExactInsensitive(actions, x))) {
            return false;
        }

        return super.test(e);
    }
}
