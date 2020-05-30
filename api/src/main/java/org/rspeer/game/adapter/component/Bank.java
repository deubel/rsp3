package org.rspeer.game.adapter.component;

import org.rspeer.commons.Time;
import org.rspeer.game.adapter.scene.SceneObject;
import org.rspeer.game.component.*;
import org.rspeer.game.scene.SceneObjects;
import jag.game.RSInventory;

import java.util.function.Predicate;

public class Bank extends Inventory {

    private static final InterfaceAddress DEPOSIT_INVENTORY_COMPONENT = new InterfaceAddress(() ->
            Interfaces.query(InterfaceComposite.BANK)
                    .actions(x -> x.equals("Deposit inventory"))
                    .results().first()
    );

    private static final InterfaceAddress DEPOSIT_EQUIPMENT_COMPONENT = new InterfaceAddress(() ->
            Interfaces.query(InterfaceComposite.BANK)
                    .actions(x -> x.equals("Deposit equipment"))
                    .results().first()
    );

    public Bank(RSInventory provider) {
        super(provider, InventoryType.BANK.getFormat(), InventoryType.BANK.getQueries());
    }

    public boolean isOpen() {
        InterfaceComponent component = Interfaces.getDirect(InterfaceComposite.BANK, 0);
        return component != null && component.isVisible();
    }

    public boolean open() {
        SceneObject bank = SceneObjects.query()
                .actions("Use", "Bank")
                .nameContains("bank", "exchange booth")
                .results().nearest();
        return bank != null && bank.interact(x -> true);
    }

    public boolean depositInventory() {
        InterfaceComponent component = DEPOSIT_INVENTORY_COMPONENT.resolve();
        return component != null && component.isVisible() && component.interact(x -> true);
    }

    public boolean depositEquipment() {
        InterfaceComponent component = DEPOSIT_EQUIPMENT_COMPONENT.resolve();
        return component != null && component.isVisible() && component.interact(x -> true);
    }

    private boolean interact(Predicate<Item> predicate, int amount, boolean deposit) {
        String baseAction = deposit ? "Deposit-" : "Withdraw-";
        Item item = Inventory.queryOf(deposit ? InventoryType.BACKPACK : InventoryType.BANK)
                .stackSize(1)
                .filter(predicate)
                .results().first();

        if (item != null) {
            if (amount <= 1) {
                item.interact(baseAction + "-1");
                return true;
            }

            if (amount == 5) {
                return item.interact(baseAction + "5");
            }

            if (amount == 10) {
                return item.interact(baseAction + "10");
            }

            if (amount > item.getStackSize()) {
                return item.interact(baseAction + "All");
            }

            return item.interact(baseAction + "X")
                    && Time.sleepUntil(EnterInput::isOpen, 2000)
                    && EnterInput.initiate(amount);
        }

        return false;
    }

    public boolean deposit(Predicate<Item> predicate, int amount) {
        return interact(predicate, amount, true);
    }

    public boolean withdraw(Predicate<Item> predicate, int amount) {
        return interact(predicate, amount, false);
    }

    public boolean withdrawAll(Predicate<Item> predicate) {
        return withdraw(predicate, Integer.MAX_VALUE);
    }

    public boolean depositAll(Predicate<Item> predicate) {
        return deposit(predicate, Integer.MAX_VALUE);
    }

    public boolean withdraw(String name, int amount) {
        return withdraw(x -> x.getName().equals(name), amount);
    }

    public boolean withdraw(int id, int amount) {
        return withdraw(x -> x.getId() == id, amount);
    }

    public boolean deposit(String name, int amount) {
        return deposit(x -> x.getName().equals(name), amount);
    }

    public boolean deposit(int id, int amount) {
        return deposit(x -> x.getId() == id, amount);
    }
}
