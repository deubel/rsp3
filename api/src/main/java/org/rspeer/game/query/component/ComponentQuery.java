package org.rspeer.game.query.component;

import org.rspeer.commons.ArrayUtils;
import org.rspeer.game.Game;
import org.rspeer.game.adapter.component.InterfaceComponent;
import org.rspeer.game.component.InterfaceComposite;
import org.rspeer.game.query.Query;
import org.rspeer.game.query.results.ComponentQueryResults;
import jag.game.RSClient;
import jag.game.RSInterfaceComponent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ComponentQuery extends Query<InterfaceComponent, ComponentQuery, ComponentQueryResults> {

    private final Supplier<List<InterfaceComponent>> provider;

    private Predicate<String> text = null;
    private Predicate<String> tooltip = null;
    private Predicate<String> action = null;
    private Predicate<String> name = null;

    private Boolean visible = null;

    private InterfaceComponent.Type[] types = null;

    private int[] materials = null;
    private int[] contentTypes = null;
    private int[] foregrounds = null;
    private int[] modelIds = null;
    private int[] widths = null;

    private boolean includeSubComponents = false;

    public ComponentQuery(Supplier<List<InterfaceComponent>> provider) {
        this.provider = provider;
    }

    public ComponentQuery(boolean includeSubComponents) {
        this(() -> getInterfaces(visit(includeSubComponents)));
    }

    public ComponentQuery() {
        this(false);
    }

    private static List<InterfaceComponent> getInterfaces(BiConsumer<List<InterfaceComponent>, InterfaceComponent> visitor) {
        List<InterfaceComponent> interfaces = new ArrayList<>();
        RSClient client = Game.getClient();
        RSInterfaceComponent[][] raw = client.getInterfaces();
        for (int i = 0; i < raw.length; i++) {
            interfaces.addAll(getInterfaces(i, visitor));
        }
        return interfaces;
    }

    private static List<InterfaceComponent> getInterfaces(int group, BiConsumer<List<InterfaceComponent>, InterfaceComponent> visitor) {
        List<InterfaceComponent> interfaces = new ArrayList<>();
        RSClient client = Game.getClient();
        RSInterfaceComponent[][] raw = client.getInterfaces();
        if (group < 0 || group >= raw.length) {
            return interfaces;
        }

        RSInterfaceComponent[] grouped = raw[group];
        if (grouped == null) {
            return interfaces;
        }

        for (RSInterfaceComponent rsi : grouped) {
            if (rsi == null) {
                continue;
            }
            visitor.accept(interfaces, new InterfaceComponent(rsi));
        }
        return interfaces;
    }

    private static BiConsumer<List<InterfaceComponent>, InterfaceComponent> visit(boolean includeSubComponents) {
        return (all, component) -> {
            all.add(component);
            if (includeSubComponents) {
                all.addAll(component.getSubComponents());
            }
        };
    }

    @Override
    public ComponentQuery self() {
        return this;
    }

    @Override
    public Supplier<List<InterfaceComponent>> getDefaultProvider() {
        return provider;
    }

    @Override
    protected ComponentQueryResults createQueryResults(Collection<InterfaceComponent> raw) {
        return new ComponentQueryResults(raw);
    }

    public ComponentQuery groups(int... groups) {
        return provider(() -> {
            List<InterfaceComponent> components = new ArrayList<>();
            for (int group : groups) {
                components.addAll(getInterfaces(group, visit(includeSubComponents)));
            }
            return components;
        });
    }

    public ComponentQuery groups(InterfaceComposite... groups) {
        return provider(() -> {
            List<InterfaceComponent> components = new ArrayList<>();
            for (InterfaceComposite group : groups) {
                components.addAll(getInterfaces(group.getGroup(), visit(includeSubComponents)));
            }
            return components;
        });
    }

    public ComponentQuery texts(Predicate<String> text) {
        this.text = text;
        return self();
    }

    public ComponentQuery tooltips(Predicate<String> tooltip) {
        this.tooltip = tooltip;
        return self();
    }

    public ComponentQuery names(Predicate<String> name) {
        this.name = name;
        return self();
    }

    public ComponentQuery actions(Predicate<String> action) {
        this.action = action;
        return self();
    }

    public ComponentQuery widths(int... widths) {
        this.widths = widths;
        return self();
    }

    public ComponentQuery materials(int... materials) {
        this.materials = materials;
        return self();
    }

    public ComponentQuery types(InterfaceComponent.Type... types) {
        this.types = types;
        return self();
    }

    public ComponentQuery contentTypes(int... contentTypes) {
        this.contentTypes = contentTypes;
        return self();
    }

    public ComponentQuery foregrounds(int... foregrounds) {
        this.foregrounds = foregrounds;
        return self();
    }

    public ComponentQuery models(int... modelIds) {
        this.modelIds = modelIds;
        return self();
    }

    public ComponentQuery includeSubComponents() {
        includeSubComponents = true;
        return self();
    }

    public ComponentQuery visible(boolean visible) {
        this.visible = visible;
        return self();
    }

    public ComponentQuery visible() {
        return visible(true);
    }

    @Override
    public boolean test(InterfaceComponent cmp) {
        if (visible != null && cmp.isVisible() != visible) {
            return false;
        }

        if (materials != null && !ArrayUtils.contains(materials, cmp.getMaterialId())) {
            return false;
        }

        if (types != null && !ArrayUtils.contains(types, cmp.getType())) {
            return false;
        }

        if (contentTypes != null && !ArrayUtils.contains(contentTypes, cmp.getContentType())) {
            return false;
        }

        if (foregrounds != null && !ArrayUtils.contains(foregrounds, cmp.getForeground())) {
            return false;
        }

        if (modelIds != null && !ArrayUtils.contains(modelIds, cmp.getModelId())) {
            return false;
        }

        if (text != null && !text.test(cmp.getText())) {
            return false;
        }

        if (name != null && !name.test(cmp.getName())) {
            return false;
        }

        if (action != null && !cmp.containsAction(action)) {
            return false;
        }        
        
        if (tooltip != null && !tooltip.test(cmp.getToolTip())) {
            return false;
        }

        if (widths != null && !ArrayUtils.contains(widths, cmp.getMaterialId())) {
            return false;
        }
        
        return super.test(cmp);
    }
}
