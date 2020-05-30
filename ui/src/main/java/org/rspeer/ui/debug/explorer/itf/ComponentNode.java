package org.rspeer.ui.debug.explorer.itf;

import org.rspeer.game.adapter.component.InterfaceComponent;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import java.util.ArrayList;
import java.util.List;

class ComponentNode extends DefaultMutableTreeNode {

    final List<ComponentNode> children = new ArrayList<>();
    final String prefix;
    final InterfaceComponent component;

    ComponentNode(String prefix, InterfaceComponent component) {
        this.prefix = prefix;
        this.component = component;
        setUserObject(toString());
    }

    ComponentNode(InterfaceComponent component) {
        this("", component);
    }

    @Override
    public String toString() {
        if (prefix.isEmpty()) {
            return String.valueOf(component.getIndex());
        }
        return prefix + component.toAddress().toShortString();
    }

    @Override
    public void add(MutableTreeNode child) {
        super.add(child);
        if (child instanceof ComponentNode) {
            children.add((ComponentNode) child);
        }
    }
}