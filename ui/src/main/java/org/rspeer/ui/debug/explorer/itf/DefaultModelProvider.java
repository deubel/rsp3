package org.rspeer.ui.debug.explorer.itf;

import org.rspeer.game.Game;
import org.rspeer.game.adapter.component.InterfaceComponent;
import jag.game.RSInterfaceComponent;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import java.util.function.Supplier;

public class DefaultModelProvider implements Supplier<TreeModel> {

    @Override
    public TreeModel get() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
        RSInterfaceComponent[][] raw = Game.getClient().getInterfaces();
        if (raw != null) {
            for (int i = 0; i < raw.length; i++) {
                RSInterfaceComponent[] group = raw[i];
                if (group == null) {
                    continue;
                }

                InterfaceNode top = new InterfaceNode(i);
                for (RSInterfaceComponent component : group) {
                    if (component == null) {
                        continue;
                    }

                    InterfaceComponent wrapped = new InterfaceComponent(component);
                    ComponentNode node = new ComponentNode(wrapped);

                    for (InterfaceComponent sub : wrapped.getSubComponents()) {
                        node.add(new ComponentNode(sub));
                    }
                    top.add(node);
                }

                root.add(top);
            }
        }

        return new DefaultTreeModel(root);
    }
}
