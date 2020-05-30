package org.rspeer.game.query.component;

import org.rspeer.commons.ArrayUtils;
import org.rspeer.commons.Range;
import org.rspeer.game.adapter.type.Actionable;
import org.rspeer.game.adapter.type.Identifiable;
import org.rspeer.game.adapter.type.Nameable;
import org.rspeer.game.component.Item;
import org.rspeer.game.query.Query;
import org.rspeer.game.query.results.ItemQueryResults;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class ItemQuery extends Query<Item, ItemQuery, ItemQueryResults> implements
        Actionable.Query<ItemQuery>,
        Identifiable.Query<ItemQuery>,
        Nameable.Query<ItemQuery> {

    private final Supplier<List<Item>> provider;

    private Boolean stackable = null;
    private Boolean noted = null;

    private Range count = null;
    private Range stackSize = null;

    private int[] ids = null;
    private int[] slots = null;

    private String[] names = null;
    private String[] nameContains = null;
    private String[] actions = null;

    public ItemQuery(Supplier<List<Item>> provider) {
        this.provider = provider;
    }

    @Override
    public Supplier<List<Item>> getDefaultProvider() {
        return provider;
    }

    @Override
    protected ItemQueryResults createQueryResults(Collection<Item> raw) {
        return new ItemQueryResults(raw);
    }

    public ItemQuery ids(int... ids) {
        this.ids = ids;
        return self();
    }

    public ItemQuery names(String... names) {
        this.names = names;
        return self();
    }

    public ItemQuery nameContains(String... names) {
        this.nameContains = names;
        return self();
    }

    public ItemQuery actions(String... actions) {
        this.actions = actions;
        return self();
    }

    public ItemQuery slots(int... slots) {
        this.slots = slots;
        return self();
    }

    public ItemQuery stackable() {
        stackable = true;
        return self();
    }

    public ItemQuery nonstackable() {
        stackable = false;
        return self();
    }

    public ItemQuery noted() {
        noted = true;
        return self();
    }

    public ItemQuery unnoted() {
        noted = false;
        return self();
    }

    public ItemQuery stackSize(int minInclusive) {
        return stackSize(minInclusive, Integer.MAX_VALUE);
    }

    public ItemQuery stackSize(int minInclusive, int maxInclusive) {
        stackSize = Range.of(minInclusive, maxInclusive);
        return self();
    }

    public ItemQuery count(int minInclusive) {
        return count(minInclusive, Integer.MAX_VALUE);
    }

    public ItemQuery count(int minInclusive, int maxInclusive) {
        stackSize = Range.of(minInclusive, maxInclusive);
        return self();
    }

    @Override
    public boolean test(Item item) {
        if (ids != null && !ArrayUtils.contains(ids, item.getId())) {
            return false;
        }

        if (names != null && !ArrayUtils.containsExactInsensitive(names, item.getName())) {
            return false;
        }

        if (nameContains != null && !ArrayUtils.containsPartialInsensitive(nameContains, item.getName())) {
            return false;
        }

        if (stackable != null && stackable != item.isStackable()) {
            return false;
        }

        if (noted != null && noted != item.isNoted()) {
            return false;
        }

        if (stackSize != null && !stackSize.within(item.getStackSize())) {
            return false;
        }

        if (count != null) {
            int total = 0;
            for (Item e : provider.get()) {
                if (e.getId() == item.getId()) {
                    total++;
                }
            }
            if (!count.within(total)) {
                return false;
            }
        }

        if (slots != null && !ArrayUtils.contains(slots, item.getIndex())) {
            return false;
        }

        if (actions != null && !item.containsAction(x -> ArrayUtils.containsExactInsensitive(actions, x))) {
            return false;
        }

        return super.test(item);
    }

    @Override
    public ItemQuery self() {
        return this;
    }
}
