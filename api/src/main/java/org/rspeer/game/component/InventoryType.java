package org.rspeer.game.component;

import org.rspeer.game.adapter.component.InterfaceComponent;
import org.rspeer.game.adapter.component.Inventory;
import org.rspeer.game.query.component.ComponentQuery;

import java.util.Arrays;
import java.util.List;

public enum InventoryType {

    /* keys
        public static final int VARROCK_GENERAL_STORE = 4;
    public static final int VARROCK_RUNE_STORE = 5;
    public static final int VARROCK_STAFF_STORE = 51;
    public static final int PRICE_CHECKER = 90;
    public static final int BARROWS = 141;
    public static final int EXCHANGE_COLLECTION = 518;
     */

    BACKPACK(93, Inventory.Format.DYNAMIC, Inventory.DataSource.COMPONENT,
            Interfaces.query().groups(InterfaceComposite.BANK_INVENTORY).filter(x -> x.getSubComponents().size() == 28),
            Interfaces.query().groups(InterfaceComposite.GRAND_EXCHANGE_INVENTORY),
            Interfaces.query().groups(InterfaceComposite.DEPOSIT_BOX).filter(x -> x.getSubComponents().size() == 28),
            Interfaces.query().groups(InterfaceComposite.TRADE_INVENTORY),
            Interfaces.query().groups(InterfaceComposite.SHOP_INVENTORY),
            Interfaces.query().groups(InterfaceComposite.PARTY_ROOM_INVENTORY),
            Interfaces.query().groups(InterfaceComposite.EQUIPMENT_OVERVIEW_INVENTORY).filter(x -> x.getSubComponents().size() == 28),
            Interfaces.query().groups(InterfaceComposite.VIEW_GUIDE_PRICES_INVENTORY),
            Interfaces.query().groups(InterfaceComposite.INVENTORY)),

    BANK(95, Inventory.Format.SUBCOMPONENTS_OF, Inventory.DataSource.COMPONENT,
            Interfaces.query()
                    .groups(InterfaceComposite.BANK)
                    .filter(x -> x.getSubComponents().size() > 800)),

    EQUIPMENT(94, Inventory.Format.SUBCOMPONENTS_OF, Inventory.DataSource.INVENTORY,
            Interfaces.query(true)
                    .groups(InterfaceComposite.WORN_EQUIPMENT)
                    .filter(InterfaceComponent::isSubComponent)
                    .filter(x -> x.getItem() != null));

    private final long key;
    private final Inventory.Format format;
    private final Inventory.DataSource preferredDataSource;
    private final List<ComponentQuery> queries;

    InventoryType(long key,
            Inventory.Format format,
            Inventory.DataSource preferredDataSource,
            ComponentQuery... queries) {
        this.key = key;
        this.format = format;
        this.preferredDataSource = preferredDataSource;
        this.queries = Arrays.asList(queries);
    }

    public static InventoryType valueOf(long key) {
        for (InventoryType composite : InventoryType.values()) {
            if (composite.key == key) {
                return composite;
            }
        }
        return null;
    }

    public long getKey() {
        return key;
    }

    public Inventory.Format getFormat() {
        return format;
    }

    public Inventory.DataSource getPreferredDataSource() {
        return preferredDataSource;
    }

    public List<ComponentQuery> getQueries() {
        return queries;
    }
}
