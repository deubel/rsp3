package org.rspeer.game.component;

import org.rspeer.commons.StringCommons;
import org.rspeer.game.Vars;
import org.rspeer.game.adapter.component.InterfaceComponent;
import org.rspeer.game.query.component.ItemQuery;
import org.rspeer.game.query.results.ComponentQueryResults;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntSupplier;

enum Interface implements Production {

    CREATION(InterfaceComposite.CREATION, () -> 0),
    JEWELLERY(InterfaceComposite.JEWELLERY_CREATION, () -> Vars.get(2224)),
    SMITHING(InterfaceComposite.SMITHING, () -> 0);

    private final InterfaceComposite composite;
    private final IntSupplier x;

    Interface(InterfaceComposite composite, IntSupplier x) {
        this.composite = composite;
        this.x = x;
    }

    @Override
    public boolean initiate(Function<ItemQuery, Item> item) {
        Item target = item.apply(query());
        return target != null && target.interact(x -> true);
    }

    @Override
    public boolean initiate(int index) {
        return initiate(query -> query.results().get(index));
    }

    @Override
    public Amount getAmount() {
        for (InterfaceComponent component : getAmountComponents()) {
            InterfaceComponent label = component.querySubComponents()
                    .types(InterfaceComponent.Type.LABEL)
                    .results().first();
            if (label != null && label.isVisible() && label.getConfig() == 0) {
                String key = StringCommons.sanitize(label.getText());
                for (Amount amount : Amount.values()) {
                    if (key.equals(amount.key)) {
                        return amount;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public boolean setAmount(Amount amount) {
        //TODO x quantity
        InterfaceComponent component = getAmountComponent(amount.key);
        return component != null && component.interact(x -> true);
    }

    private ComponentQueryResults getAmountComponents() {
        return Interfaces.query(composite)
                .actions(x -> x.equals("Quantity:"))
                .results();
    }

    private InterfaceComponent getAmountComponent(String quantity) {
        for (InterfaceComponent component : getAmountComponents()) {
            InterfaceComponent label = component.querySubComponents()
                    .types(InterfaceComponent.Type.LABEL)
                    .results().first();
            if (label != null && label.isVisible()) {
                String value = StringCommons.sanitize(label.getText());
                if (quantity.equals(value)) {
                    return component;
                }
            }
        }
        return null;
    }

    @Override
    public int getXAmount() {
        return x.getAsInt();
    }

    @Override
    public ItemQuery query() {
        ComponentQueryResults results = Interfaces.query(composite)
                .actions(x -> !x.isEmpty())
                .types(InterfaceComponent.Type.MODEL)
                .models(-1)
                .visible()
                .results();
        List<Item> items = new ArrayList<>();
        for (InterfaceComponent item : results) {
            items.add(Item.direct(item));
        }
        return new ItemQuery(() -> items);
    }

    @Override
    public boolean isOpen() {
        InterfaceComponent component = Interfaces.getDirect(composite, 0);
        return component != null && component.isVisible();
    }
}

/**
 * An interface for the manipulation of various production interfaces.
 * This includes the cooking interface, jewellery creation and the anvil smithing interface.
 */
public interface Production {

    static Production getActive() {
        for (Interface itf : Interface.values()) {
            if (itf.isOpen()) {
                return itf;
            }
        }
        return null;
    }

    boolean initiate(Function<ItemQuery, Item> item);

    boolean initiate(int index);

    Amount getAmount();

    boolean setAmount(Amount amount);

    int getXAmount();

    ItemQuery query();

    boolean isOpen();

    enum Amount {

        ONE(1, "1"),
        FIVE(5, "5"),
        TEN(10, "10"),
        ALL(-1, "All"),
        X(-2, "X");

        final int quantity;
        final String key;

        Amount(int quantity, String key) {
            this.quantity = quantity;
            this.key = key;
        }
    }
}
