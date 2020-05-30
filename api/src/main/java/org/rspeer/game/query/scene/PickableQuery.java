package org.rspeer.game.query.scene;

import org.rspeer.commons.ArrayUtils;
import org.rspeer.commons.Range;
import org.rspeer.game.adapter.scene.Pickable;
import org.rspeer.game.position.Position;
import org.rspeer.game.query.results.SceneNodeQueryResults;
import org.rspeer.game.scene.Pickables;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class PickableQuery extends SceneNodeQuery<Pickable, PickableQuery> {

    private final Supplier<List<Pickable>> provider;


    private Boolean stackable = null;
    private Boolean noted = null;

    private Range amount = null;

    private int[] ids = null;

    private String[] names = null;
    private String[] nameContains = null;
    private String[] actions = null;

    public PickableQuery(Supplier<List<Pickable>> provider) {
        this.provider = provider;
    }

    public PickableQuery nameContains(String... names) {
        this.nameContains = names;
        return self();
    }

    public PickableQuery actions(String... actions) {
        this.actions = actions;
        return self();
    }

    public PickableQuery stackable() {
        stackable = true;
        return self();
    }

    public PickableQuery nonstackable() {
        stackable = false;
        return self();
    }

    public PickableQuery noted() {
        noted = true;
        return self();
    }

    public PickableQuery unnoted() {
        noted = false;
        return self();
    }

    public PickableQuery amount(int minInclusive) {
        return amount(minInclusive, Integer.MAX_VALUE);
    }

    public PickableQuery amount(int minInclusive, int maxInclusive) {
        amount = Range.of(minInclusive, maxInclusive);
        return self();
    }

    public PickableQuery on(Position... positions) {
        provider(() -> {
            List<Pickable> all = new ArrayList<>();
            for (Position position : positions) {
                all.addAll(Pickables.getAt(position));
            }
            return all;
        });
        return super.on(positions);
    }

    public PickableQuery names(String... names) {
        this.names = names;
        return this;
    }

    public PickableQuery ids(int... ids) {
        this.ids = ids;
        return this;
    }

    @Override
    public Supplier<List<Pickable>> getDefaultProvider() {
        return provider;
    }

    @Override
    protected SceneNodeQueryResults<Pickable> createQueryResults(Collection<Pickable> raw) {
        return new SceneNodeQueryResults<>(raw);
    }

    @Override
    public PickableQuery self() {
        return this;
    }

    @Override
    public boolean test(Pickable item) {
        if (ids != null && !ArrayUtils.contains(ids, item.getId())) {
            return false;
        }

        if (names != null && !ArrayUtils.containsExactInsensitive(names, item.getName())) {
            return false;
        }

        if (nameContains != null && !ArrayUtils.containsPartialInsensitive(nameContains, item.getName())) {
            return false;
        }

        if (stackable != null && stackable != item.getDefinition().isStackable()) {
            return false;
        }

        if (noted != null && noted != item.getDefinition().isNoted()) {
            return false;
        }

        if (amount != null && !amount.within(item.getStackSize())) {
            return false;
        }

        if (actions != null && !item.containsAction(x -> ArrayUtils.containsExactInsensitive(actions, x))) {
            return false;
        }

        return super.test(item);
    }
}
