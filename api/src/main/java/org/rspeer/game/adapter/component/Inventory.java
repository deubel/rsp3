package org.rspeer.game.adapter.component;

import org.rspeer.game.Game;
import org.rspeer.game.adapter.Adapter;
import org.rspeer.game.adapter.component.tab.Backpack;
import org.rspeer.game.component.InventoryType;
import org.rspeer.game.component.Item;
import org.rspeer.game.query.component.ComponentQuery;
import org.rspeer.game.query.component.ItemQuery;
import org.rspeer.game.query.results.ComponentQueryResults;
import org.rspeer.game.query.results.ItemQueryResults;
import jag.RSNodeTable;
import jag.game.RSInventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * A class for the manipulation of item containers.
 * This includes but is not limited to inventory, bank and equipment.
 * Please note that static lookup methods in this class are null-safe
 */
public class Inventory extends Adapter<RSInventory> {

    private final Format format;
    private final List<ComponentQuery> queries;

    public Inventory(RSInventory provider, Format format, List<ComponentQuery> queries) {
        super(provider);
        this.format = format;
        this.queries = queries;
    }

    public static Backpack backpack() {
        RSNodeTable<RSInventory> table = Game.getClient().getInventories();
        if (table != null) {
            RSInventory node = table.lookup(InventoryType.BACKPACK.getKey());
            if (node != null) {
                return new Backpack(node);
            }
        }
        return new Backpack(NullSafeInventory.Provider.INSTANCE);
    }

    public static Inventory equipment() {
        return Inventory.get(InventoryType.EQUIPMENT);
    }

    public static Bank bank() {
        RSNodeTable<RSInventory> table = Game.getClient().getInventories();
        if (table != null) {
            RSInventory node = table.lookup(InventoryType.BANK.getKey());
            if (node != null) {
                return new Bank(node);
            }
        }
        return new Bank(NullSafeInventory.Provider.INSTANCE);
    }

    public static ItemQuery queryOf(InventoryType type) {
        Inventory inventory = Inventory.get(type);
        if (inventory == null) {
            return new ItemQuery(Collections::emptyList);
        }
        return inventory.query();
    }

    public static Inventory get(long key, Format format, List<ComponentQuery> queries) {
        RSNodeTable<RSInventory> table = Game.getClient().getInventories();
        if (table != null) {
            RSInventory node = table.lookup(key);
            if (node != null) {
                return new Inventory(node, format, queries);
            }
        }
        return NullSafeInventory.INSTANCE;
    }

    public static Inventory get(InventoryType composite) {
        return get(composite.getKey(), composite.getFormat(), composite.getQueries());
    }

    //maybe make this non static too? its not container specific but i guess it can be in the future?
    //also looks cleaner maybe
    public static boolean isItemSelected() {
        return Game.getClient().getItemSelectionState() != 0;
    }

    public long getKey() {
        RSInventory provider = getProvider();
        return provider != null ? provider.getKey() : -1;
    }

    public boolean isUnbound() {
        return queries == null || queries.isEmpty();
    }

    private List<Item> parseItems() {
        List<Item> items = new ArrayList<>();
        if (!isUnbound()) {
            for (ComponentQuery query : queries) {
                ComponentQueryResults results = query.results();
                if (!results.isEmpty()) {
                    format.parse(query.results(), items);
                    break;
                }
            }
        }

        if (!items.isEmpty()) {
            return items;
        }

        RSInventory provider = getProvider();
        if (provider == null) {
            return items;
        }

        int[] ids = provider.getIds();
        int[] stk = provider.getStackSizes();
        if (ids != null && stk != null && ids.length == stk.length) {
            for (int i = 0; i < ids.length; i++) {
                int id = ids[i];
                if (!Item.isNullId(id)) {
                    items.add(Item.unbound(i, id, stk[i]));
                }
            }
        }
        return items;
    }

    public ItemQueryResults getItems() {
        return query().results();
    }

    public ItemQueryResults getItems(Predicate<Item> predicate) {
        return query().filter(predicate).results();
    }

    public ItemQueryResults getItems(int... ids) {
        return query().ids(ids).results();
    }

    public ItemQueryResults getItems(String... names) {
        return query().names(names).results();
    }

    public ItemQuery query() {
        return new ItemQuery(this::parseItems);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Inventory<").append(getKey()).append(">[");
        List<Item> items = parseItems();
        int ptr = 0;
        for (Item item : items) {
            sb.append(item);
            if (ptr++ <= items.size()) {
                sb.append(", ");
            }
        }
        return sb.append("]").toString();
    }

    public enum DataSource {
        INVENTORY, //itemtable
        COMPONENT;
    }

    public enum Format {

        TABLE {
            @Override
            public void parse(ComponentQueryResults results, List<Item> items) {
                InterfaceComponent component = results.first();
                if (component != null) {
                    int slots = component.getWidth() * component.getHeight();
                    for (int i = 0; i < slots; i++) {
                        Item item = Item.getTableElement(component, i);
                        if (item != null && item.getId() > 0) {
                            items.add(item);
                        }
                    }
                }
            }
        }, SUBCOMPONENTS_OF {
            @Override
            public void parse(ComponentQueryResults results, List<Item> items) {
                InterfaceComponent component = results.first();
                if (component != null) {
                    for (InterfaceComponent sub : component.getSubComponents()) {
                        Item item = Item.direct(sub);
                        if (item != null && item.getId() > 0) {
                            items.add(item);
                        }
                    }
                }
            }
        }, INDIVIDUAL {
            @Override
            public void parse(ComponentQueryResults results, List<Item> items) {
                for (InterfaceComponent sub : results) {
                    Item item = Item.direct(sub);
                    if (item != null && item.getId() > 0) {
                        items.add(item);
                    }
                }
            }
        }, DYNAMIC {
            @Override
            public void parse(ComponentQueryResults results, List<Item> items) {
                InterfaceComponent component = results.first();
                if (component != null) {
                    if (component.getType() == InterfaceComponent.Type.TABLE) {
                        TABLE.parse(results, items);
                        return;
                    }

                    if (results.size() > 1) {
                        SUBCOMPONENTS_OF.parse(results, items);
                        return;
                    }

                    INDIVIDUAL.parse(results, items);
                }
            }
        };

        public abstract void parse(ComponentQueryResults results, List<Item> items);
    }

    //TODO generic itemcontainer methods in here
}
