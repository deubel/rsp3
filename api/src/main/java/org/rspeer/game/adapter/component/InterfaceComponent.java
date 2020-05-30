package org.rspeer.game.adapter.component;

import org.rspeer.commons.StringCommons;
import org.rspeer.game.Game;
import org.rspeer.game.action.Action;
import org.rspeer.game.action.ActionOpcode;
import org.rspeer.game.action.tree.ButtonAction;
import org.rspeer.game.action.tree.ComponentAction;
import org.rspeer.game.action.tree.DefaultComponentAction;
import org.rspeer.game.adapter.Adapter;
import org.rspeer.game.adapter.type.Interactable;
import org.rspeer.game.adapter.type.Nameable;
import org.rspeer.game.component.ComponentConfig;
import org.rspeer.game.component.Dialog;
import org.rspeer.game.component.InterfaceAddress;
import org.rspeer.game.component.Item;
import org.rspeer.game.component.tab.Magic;
import org.rspeer.game.query.component.ComponentQuery;
import jag.RSIntegerNode;
import jag.RSNodeTable;
import jag.game.RSInterfaceComponent;
import jag.game.RSSubInterface;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InterfaceComponent extends Adapter<RSInterfaceComponent> implements Interactable, Nameable {

    public InterfaceComponent(RSInterfaceComponent provider) {
        super(provider);
    }

    public boolean isVisible() {
        RSInterfaceComponent provider = getProvider();
        return provider != null && provider.getRenderCycle() + 20 >= Game.getEngineCycle() && !provider.isExplicitlyHidden();
    }

    public int getUid() {
        RSInterfaceComponent provider = getProvider();
        return provider != null ? provider.getUid() : -1;
    }

    public int getParentUid() {
        RSInterfaceComponent provider = getProvider();
        return provider != null ? provider.getParentUid() : -1;
    }

    public int getMaterialId() {
        RSInterfaceComponent provider = getProvider();
        return provider != null ? provider.getMaterialId() : -1;
    }

    public int getSpriteId() {
        RSInterfaceComponent provider = getProvider();
        return provider != null ? provider.getSpriteId() : -1;
    }

    public Type getType() {
        RSInterfaceComponent provider = getProvider();
        return provider != null ? Type.valueOf(provider.getType()) : Type.UNDEFINED;
    }

    public int getContentType() {
        RSInterfaceComponent provider = getProvider();
        return provider != null ? provider.getContentType() : -1;
    }

    public int getButtonType() {
        RSInterfaceComponent provider = getProvider();
        return provider != null ? provider.getButtonType() : -1;
    }

    public int getForeground() {
        RSInterfaceComponent provider = getProvider();
        return provider != null ? provider.getForeground() : -1;
    }

    public int getShadowColor() {
        RSInterfaceComponent provider = getProvider();
        return provider != null ? provider.getShadowColor() : -1;
    }

    public int getModelId() {
        RSInterfaceComponent provider = getProvider();
        return provider != null ? provider.getModelId() : -1;
    }

    public int getModelType() {
        RSInterfaceComponent provider = getProvider();
        return provider != null ? provider.getModelType() : -1;
    }

    public int getWidth() {
        RSInterfaceComponent provider = getProvider();
        return provider != null ? provider.getWidth() : -1;
    }

    public int getHeight() {
        RSInterfaceComponent provider = getProvider();
        return provider != null ? provider.getHeight() : -1;
    }

    public int getConfig() {
        RSInterfaceComponent provider = getProvider();
        if (provider == null) {
            return -1;
        }

        RSNodeTable<RSIntegerNode> configs = Game.getClient().getInterfaceConfigs();
        if (configs == null) {
            return provider.getConfig();
        }
        RSIntegerNode node = configs.lookup(((long) getUid() << 32) + (long) getSubComponentIndex());
        return node != null ? node.getValue() : provider.getConfig();
    }

    public boolean transmits(Object o) {
        RSInterfaceComponent provider = getProvider();
        return provider != null && provider.transmits(o);
    }

    public String getText() {
        RSInterfaceComponent provider = getProvider();
        if (provider == null) {
            return "";
        }

        String text = provider.getText();
        return text != null ? StringCommons.replaceJagspace(text) : "";
    }

    public String getName() {
        RSInterfaceComponent provider = getProvider();
        if (provider == null) {
            return "";
        }

        String name = provider.getName();
        return name != null ? StringCommons.replaceJagspace(name) : "";
    }

    @Override
    public List<String> getActions() {
        RSInterfaceComponent provider = getProvider();
        if (provider == null) {
            return Collections.emptyList();
        }

        List<String> actions = new ArrayList<>();
        if (provider.getActions() == null) {
            return actions;
        }

        for (String action : provider.getActions()) {
            if (action != null) {
                actions.add(StringCommons.replaceColorTag(action));
            }
        }
        return actions;
    }

    @Override
    public String[] getRawActions() {
        RSInterfaceComponent provider = getProvider();
        if (provider == null) {
            return new String[0];
        }

        String[] actions = provider.getActions();
        return actions != null ? actions : new String[0];
    }

    private ButtonAction buttonActionOf(String action) {
        RSInterfaceComponent provider = getProvider();
        if (provider == null) {
            return null;
        }

        int button = getButtonType();
        if (button == 1 && action.equalsIgnoreCase(provider.getToolTip())) {
            return new ButtonAction(ActionOpcode.BUTTON_INPUT, getSubComponentIndex(), getUid());
        }

        if ((button == 2 || ComponentConfig.getSpellTargets(getConfig()) != 0)
                && !Magic.isSpellSelected() && action.equalsIgnoreCase(getSelectedActionInternal())) {
            return new ButtonAction(ActionOpcode.BUTTON_SELECTABLE_SPELL, getSubComponentIndex(), getUid());
        }

        if (button == 3 && action.equalsIgnoreCase("Close")) {
            return new ButtonAction(ActionOpcode.BUTTON_CLOSE, getSubComponentIndex(), getUid());
        } else if (button == 4 && action.equalsIgnoreCase(provider.getToolTip())) {
            return new ButtonAction(ActionOpcode.BUTTON_VAR_FLIP, getSubComponentIndex(), getUid());
        } else if (button == 5 && action.equalsIgnoreCase(provider.getToolTip())) {
            return new ButtonAction(ActionOpcode.BUTTON_VAR_SET, getSubComponentIndex(), getUid());
        } else if (button == 6 && Dialog.getPleaseWaitComponent() == null && action.equalsIgnoreCase(provider.getToolTip())) {
            return new ButtonAction(ActionOpcode.BUTTON_DIALOG, getSubComponentIndex(), getUid());
        }

        return null;
    }

    @Override
    public ComponentAction actionOf(String action) {
        RSInterfaceComponent provider = getProvider();
        if (provider == null) {
            return null;
        }

        ButtonAction buttonAction = buttonActionOf(action);
        if (buttonAction != null) {
            return buttonAction;
        }

        int config = getConfig();

        if (Magic.isSpellSelected() && action.equalsIgnoreCase("Cast")) {
            if (ComponentConfig.allowsSpells(config) && (Magic.getSpellTargetFlags() & 0x20) == 32) {
                return new DefaultComponentAction(ActionOpcode.SPELL_ON_COMPONENT, 0, getSubComponentIndex(), getUid());
            }
            return null;
        }

        int index = Action.indexOf(getRawActions(), action);
        if (index >= 5 && index <= 9) {
            return new DefaultComponentAction(ActionOpcode.COMPONENT_ACTION_2, index + 1, getSubComponentIndex(), getUid());
        }

        if (action.equalsIgnoreCase(getSelectedActionInternal())) {
            return new DefaultComponentAction(ActionOpcode.BUTTON_SELECTABLE_SPELL, 0, getSubComponentIndex(), getUid());
        }

        if (index >= 0 && index <= 4) {
            return new DefaultComponentAction(ActionOpcode.COMPONENT_ACTION, index + 1, getSubComponentIndex(), getUid());
        }

        if (ComponentConfig.isDialogOption(config) && action.equalsIgnoreCase("Continue")) {
            return new DefaultComponentAction(ActionOpcode.BUTTON_DIALOG, 0, getSubComponentIndex(), getUid());
        }
        return null;
    }

    private String getSelectedActionInternal() {
        RSInterfaceComponent provider = getProvider();
        if (provider == null) {
            return null;
        }

        if (ComponentConfig.getSpellTargets(getConfig()) == 0) {
            return null;
        }

        String selectedAction = provider.getSelectedAction();
        return selectedAction != null && selectedAction.trim().length() != 0 ? selectedAction : null;
    }

    public boolean isSubComponent() {
        return getSubComponentIndex() != -1;
    }

    public int getGroupIndex() {
        return getUid() >>> 16;
    }

    public int getIndex() {
        int comp = getSubComponentIndex();
        if (comp != -1) {
            return comp;
        }
        return getUid() & 0xFFFF;
    }

    public int getSubComponentIndex() {
        RSInterfaceComponent provider = getProvider();
        return provider != null ? provider.getSubComponentIndex() : -1;
    }

    public int getParentIndex() {
        int comp = getSubComponentIndex();
        if (comp != -1) { //is a grandchild
            return getUid() & 0xFFFF;
        }
        return getUid() >>> 16;
    }

    public InterfaceAddress toAddress() {
        return new InterfaceAddress(getGroupIndex())
                .component(isSubComponent() ? getParentIndex() : getIndex())
                .subComponent(getSubComponentIndex());
    }

    public List<String> getTableActions() {
        List<String> actions = new ArrayList<>();
        RSInterfaceComponent provider = getProvider();
        if (provider == null) {
            return actions;
        }

        String[] raw = provider.getTableActions();
        if (raw == null) {
            return actions;
        }

        for (String action : raw) {
            if (action != null) {
                actions.add(action);
            }
        }
        return actions;
    }

    public int getFontId() {
        RSInterfaceComponent provider = getProvider();
        return provider != null ? provider.getFontId() : -1;
    }

    public Rectangle getBounds() {
        RSInterfaceComponent provider = getProvider();
        if (provider == null) {
            return new Rectangle(-1, -1, -1, -1);
        }
        return new Rectangle(provider.getX(), provider.getY(), provider.getWidth(), provider.getHeight());
    }

    public InterfaceComponent getSubComponent(int index) {
        RSInterfaceComponent provider = getProvider();
        if (provider == null) {
            return null;
        }
        RSInterfaceComponent[] subs = provider.getSubComponents();
        return subs != null && index >= 0 && index < subs.length ? new InterfaceComponent(subs[index]) : null;
    }

    private List<InterfaceComponent> getDynamicSubComponents() {
        List<InterfaceComponent> subcomponents = new ArrayList<>();
        RSInterfaceComponent provider = getProvider();
        if (provider == null) {
            return subcomponents;
        }

        RSInterfaceComponent[] raw = provider.getSubComponents();
        if (raw == null) {
            return subcomponents;
        }

        for (RSInterfaceComponent component : raw) {
            if (component != null && component.getParentUid() == getUid()) {
                subcomponents.add(new InterfaceComponent(component));
            }
        }
        return subcomponents;
    }

    private List<InterfaceComponent> getStaticSubComponents() {
        List<InterfaceComponent> subcomponents = new ArrayList<>();
        if (getParentUid() == getUid()) {
            return subcomponents;
        }

        int group = getGroupIndex();
        RSInterfaceComponent[][] raw = Game.getClient().getInterfaces();
        if (raw == null || group < 0 || group >= raw.length) {
            return subcomponents;
        }

        RSInterfaceComponent[] grouped = raw[group];
        if (grouped == null) {
            return subcomponents;
        }

        for (RSInterfaceComponent component : grouped) {
            if (component != null && component.getParentUid() == getUid()) {
                subcomponents.add(new InterfaceComponent(component));
            }
        }

        return subcomponents;
    }

    private List<InterfaceComponent> getNestedSubComponents() {
        List<InterfaceComponent> subcomponents = new ArrayList<>();
        if (getParentUid() == getUid()) {
            return subcomponents;
        }

        RSNodeTable<RSSubInterface> nested = Game.getClient().getSubInterfaces();
        if (nested == null) {
            return subcomponents;
        }

        RSSubInterface sub = nested.lookup(getUid());
        if (sub == null) {
            return subcomponents;
        }

        int group = sub.getId();

        RSInterfaceComponent[][] raw = Game.getClient().getInterfaces();
        if (raw == null || group < 0 || group >= raw.length) {
            return subcomponents;
        }

        RSInterfaceComponent[] grouped = raw[group];
        if (grouped == null) {
            return subcomponents;
        }

        for (RSInterfaceComponent component : grouped) {
            if (component != null && component.getParentUid() == -1) {
                subcomponents.add(new InterfaceComponent(component));
            }
        }
        return subcomponents;
    }

    private List<InterfaceComponent> getDefaultSubComponents() {
        List<InterfaceComponent> subcomponents = new ArrayList<>();
        RSInterfaceComponent provider = getProvider();
        if (provider == null) {
            return subcomponents;
        }

        RSInterfaceComponent[] raw = provider.getSubComponents();
        if (raw == null) {
            return subcomponents;
        }

        for (RSInterfaceComponent component : raw) {
            if (component != null) {
                subcomponents.add(new InterfaceComponent(component));
            }
        }
        return subcomponents;
    }

    public List<InterfaceComponent> getSubComponents(TraversalOption option) {
        switch (option) {
            case NESTED:
                return getNestedSubComponents();
            case STATIC:
                return getStaticSubComponents();
            case DYNAMIC:
                return getDynamicSubComponents();
            case DEFAULT:
                return getDefaultSubComponents();
            default: {
                throw new IllegalArgumentException("Unsupported TraversalOption: " + option);
            }
        }
    }

    public List<InterfaceComponent> getSubComponents() {
        return getSubComponents(TraversalOption.DEFAULT);
    }

    public ComponentQuery querySubComponents() {
        return new ComponentQuery(this::getSubComponents);
    }

    public String getSelectedAction() {
        RSInterfaceComponent provider = getProvider();
        if (provider == null) {
            return "";
        }

        String text = provider.getSelectedAction();
        return text != null ? StringCommons.replaceJagspace(text) : "";
    }

    public String getSpellName() {
        RSInterfaceComponent provider = getProvider();
        if (provider == null) {
            return "";
        }

        String text = provider.getSpellName();
        return text != null ? StringCommons.replaceJagspace(text) : "";
    }

    public String getToolTip() {
        RSInterfaceComponent provider = getProvider();
        if (provider == null) {
            return "";
        }

        String text = provider.getToolTip();
        return text != null ? StringCommons.replaceJagspace(text) : "";
    }

    public Item getItem() {
        return Item.direct(this);
    }

    public enum TraversalOption {

        NESTED,
        DYNAMIC,
        STATIC,
        DEFAULT;

        private static final TraversalOption[] NATURAL = {NESTED, DYNAMIC, STATIC};

        public static TraversalOption[] natural() {
            return NATURAL;
        }
    }

    public enum Type {

        PANEL(0),
        UNKNOWN(1),
        TABLE(2),
        BOX(3),
        LABEL(4),
        SPRITE(5),
        MODEL(6),
        MEDIA(7),
        TOOLTIP(8),
        DIVIDER(9),
        UNDEFINED(-1);

        private final int id;

        Type(int id) {
            this.id = id;
        }

        public static Type valueOf(int id) {
            for (Type type : Type.values()) {
                if (type.id == id) {
                    return type;
                }
            }
            throw new RuntimeException("Unknown component type");
        }
    }
}
