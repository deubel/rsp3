package org.rspeer.ui.debug.explorer.itf;

import org.rspeer.game.component.InterfaceComposite;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import java.util.ArrayList;
import java.util.List;

class InterfaceNode extends DefaultMutableTreeNode {

    final List<ComponentNode> children = new ArrayList<>();
    final int group;
    final String name;

    InterfaceNode(int group) {
        this.group = group;
        String name = String.valueOf(group);
        InterfaceComposite composite = InterfaceComposite.valueOf(group);
        if (composite != null) {
            name = composite.toString() + "<" + group + ">";
        }

        this.name = name;
        setUserObject(name);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void add(MutableTreeNode child) {
        super.add(child);
        if (child instanceof ComponentNode) {
            children.add((ComponentNode) child);
        }
    }
}