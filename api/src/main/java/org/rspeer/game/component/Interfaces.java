package org.rspeer.game.component;

import org.rspeer.game.Game;
import org.rspeer.game.adapter.component.InterfaceComponent;
import org.rspeer.game.query.component.ComponentQuery;
import jag.game.RSInterfaceComponent;

public class Interfaces {

    public static ComponentQuery query(boolean includeSubComponents) {
        return new ComponentQuery(includeSubComponents);
    }

    public static ComponentQuery query(InterfaceComposite... groups) {
        return query().groups(groups);
    }

    public static ComponentQuery query() {
        return query(false);
    }

    public static InterfaceComponent getDirect(InterfaceComposite group, int component, int subComponent) {
        return getDirect(group.getGroup(), component, subComponent);
    }

    public static InterfaceComponent getDirect(InterfaceComposite group, int component) {
        return getDirect(group, component, -1);
    }

    public static InterfaceComponent getDirect(int group, int component) {
        return getDirect(group, component, -1);
    }

    public static InterfaceComponent getDirect(int group, int component, int subComponent) {
        RSInterfaceComponent[][] raw = Game.getClient().getInterfaces();
        if (raw == null || group < 0 || group >= raw.length) {
            return null;
        }

        RSInterfaceComponent[] grouped = raw[group];
        if (grouped == null || component < 0 || component >= grouped.length) {
            return null;
        }

        RSInterfaceComponent prov = grouped[component];
        if (prov == null) {
            return null;
        } else if (subComponent == -1) {
            return new InterfaceComponent(prov);
        }

        RSInterfaceComponent[] subs = prov.getSubComponents();
        if (subs == null || subComponent < 0 || subComponent >= subs.length) {
            return null;
        }

        return (prov = subs[subComponent]) != null ? new InterfaceComponent(prov) : null;
    }

    public static InterfaceComponent lookup(InterfaceAddress address) {
        if (address == null || address.getComponent() == -1) {
            return null; //Root reference
        }
        return getDirect(address.getRoot(), address.getComponent(), address.getSubComponent());
    }
}
