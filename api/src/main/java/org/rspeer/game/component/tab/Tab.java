package org.rspeer.game.component.tab;

import org.rspeer.game.adapter.component.InterfaceComponent;
import org.rspeer.game.component.InterfaceAddress;
import org.rspeer.game.component.InterfaceComposite;
import org.rspeer.game.component.InterfaceOptions;
import org.rspeer.game.component.Interfaces;

import java.util.function.Predicate;

public enum Tab {

    CLAN_CHAT,
    FRIENDS_LIST,
    ACCOUNT_MANAGEMENT,
    LOGOUT, //not an actual tab for line layout mode, but a button next to minimap
    OPTIONS,
    EMOTES,
    MUSIC_PLAYER,
    COMBAT,
    SKILLS,
    QUEST_LIST,
    INVENTORY,
    EQUIPMENT,
    PRAYER,
    MAGIC;

    private final InterfaceAddress fixedModeAddress;
    private final InterfaceAddress lineLayoutAddress;
    private final InterfaceAddress boxLayoutAddress;

    Tab() {
        Predicate<String> action = x -> x.toLowerCase().contains((toString().toLowerCase()));
        fixedModeAddress = new InterfaceAddress(() -> componentOf(InterfaceComposite.FIXED_MODE_VIEWPORT, action));
        lineLayoutAddress = new InterfaceAddress(() -> componentOf(InterfaceComposite.RESIZED_VIEWPORT_LINE, action));
        boxLayoutAddress = new InterfaceAddress(() -> componentOf(InterfaceComposite.RESIZED_VIEWPORT_STONE, action));
    }

    private static InterfaceComponent componentOf(InterfaceComposite group, Predicate<String> action) {
        return Interfaces.query(group).actions(action).results().first();
    }

    public int getLineLayoutIndex() {
        return lineLayoutAddress.mapToInt(InterfaceComponent::getIndex);
    }

    public int getFixedModeIndex() {
        return fixedModeAddress.mapToInt(InterfaceComponent::getIndex);
    }

    public int getBoxLayoutIndex() {
        return boxLayoutAddress.mapToInt(InterfaceComponent::getIndex);
    }

    private boolean isBoxLayout() {
        return InterfaceOptions.getTabLayout() == InterfaceOptions.TabLayout.BOX;
    }

    public int getComponentIndex() {
        if (InterfaceOptions.getViewMode() == InterfaceOptions.ViewMode.FIXED_MODE) {
            return fixedModeAddress.mapToInt(InterfaceComponent::getIndex);
        }
        return isBoxLayout() ? getBoxLayoutIndex() : getLineLayoutIndex();
    }

    public InterfaceComponent getComponent() {
        if (InterfaceOptions.getViewMode() == InterfaceOptions.ViewMode.FIXED_MODE) {
            return Interfaces.lookup(fixedModeAddress);
        }
        return Interfaces.lookup(isBoxLayout() ? boxLayoutAddress : lineLayoutAddress);
    }

    @Override
    public String toString() {
        String name = super.name();
        return name.charAt(0) + name.substring(1).toLowerCase().replace("_", " ");
    }
}
